package com.eatthepath.idobfuscator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>Converts integers to strings using an arbitrary alphabet. As with a "normal" string representation of a number,
 * each character in a string produced by this codec represents a numeric value, and the position of the character
 * determines its place value; the radix is equal to the number of characters in the codec's alphabet.</p>
 *
 * <p>The order of characters in the codec's alphabet is significant, which allows callers to "shuffle" the alphabet
 * to frustrate unwanted attempts at deobfuscation.</p>
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class AlphabetCodec implements IntegerCodec, LongCodec {

    private final char[] alphabet;
    private final Map<Character, Integer> charactersToValues = new HashMap<>();

    private final int stringLengthForInts;
    private final int stringLengthForLongs;

    private final long placeValues[];

    /**
     * <p>Constructs a new alphabet codec that uses the given alphabet to encode and decode integers. Note that the
     * order of characters in the given alphabet is significant; swapping the position of two characters in the given
     * alphabet also swaps the values represented by those characters.</p>
     *
     * <p>Legal alphabets must meet certain criteria:</p>
     *
     * <ul>
     *  <li>Alphabets must contain at least two characters</li>
     *  <li>Alphabets must not contain any repeated characters</li>
     * </ul>
     *
     * @param alphabet the alphabet to use when encoding or decoding integers
     *
     * @see com.eatthepath.idobfuscator.util.AlphabetBuilder
     */
    public AlphabetCodec(final char... alphabet) {
        Objects.requireNonNull(alphabet, "Alphabet must not be null.");

        if (alphabet.length < 2) {
            throw new IllegalArgumentException("Alphabet must contain at least two characters");
        }

        for (int i = 0; i < alphabet.length; i++) {
            for (int j = i + 1; j < alphabet.length; j++) {
                if (alphabet[i] == alphabet[j]) {
                    throw new IllegalArgumentException(String.format("Alphabet contains character '%s' more than once", alphabet[i]));
                }
            }
        }

        this.alphabet = alphabet;

        for (int i = 0; i < alphabet.length; i++) {
            this.charactersToValues.put(alphabet[i], i);
        }

        // Based on the size of the alphabet and the width of an integer, we can determine the maximum length of a
        // string needed to represent any integer value with the given alphabet. With that, we can both perform some
        // low-cost error-checking when we try to decode strings and also precalculate place values to avoid repeating
        // work when decoding.
        this.stringLengthForInts = (int) Math.ceil(Math.log(Math.pow(2, Integer.SIZE)) / Math.log(this.alphabet.length));
        this.stringLengthForLongs = (int) Math.ceil(Math.log(Math.pow(2, Long.SIZE)) / Math.log(this.alphabet.length));

        this.placeValues = new long[this.stringLengthForLongs];
        this.placeValues[0] = 1;

        for (int i = 1; i < this.placeValues.length; i++) {
            this.placeValues[i] = this.placeValues[i - 1] * this.alphabet.length;
        }
    }

    /**
     * Encodes the given integer as a string using this codec's alphabet.
     *
     * @param i the integer to encode
     *
     * @return a string representation of the given integer
     */
    @Override
    public String encodeIntegerAsString(final int i) {
        return this.encodeLongAsString(i & 0xffffffffL, this.stringLengthForInts);
    }

    /**
     * Encodes the given long integer as a string using this codec's alphabet.
     *
     * @param l the long integer to encode
     *
     * @return a string representation of the given long integer
     */
    @Override
    public String encodeLongAsString(final long l) {
        return this.encodeLongAsString(l, this.stringLengthForLongs);
    }

    private String encodeLongAsString(final long l, final int stringLength) {
        long workingCopy = l;
        final char[] encodedCharacters = new char[stringLength];

        for (int j = encodedCharacters.length - 1; j >= 0; j--) {
            encodedCharacters[j] = this.alphabet[(int) Long.remainderUnsigned(workingCopy, this.alphabet.length)];
            workingCopy = Long.divideUnsigned(workingCopy, this.alphabet.length);
        }

        return new String(encodedCharacters);
    }

    /**
     * Decodes the given string as an integer.
     *
     * @param string the string to decode as an integer
     *
     * @return the integer represented by the given string
     *
     * @throws IllegalArgumentException if the given string is too long to represent a valid integer using this codec's
     * alphabet
     * @throws IllegalArgumentException if the given string contains characters not in this codec's alphabet
     */
    @Override
    public int decodeStringAsInteger(final String string) {
        if (string.length() > this.stringLengthForInts) {
            throw new IllegalArgumentException(String.format("String \"%s\" is too long to represent a valid integer.", string));
        }

        return (int) this.decodeStringAsLong(string);
    }

    /**
     * Decodes the given string as a long integer.
     *
     * @param string the string to decode as a long integer
     *
     * @return the long integer represented by the given string
     *
     * @throws IllegalArgumentException if the given string is too long to represent a valid long integer using this
     * codec's alphabet
     * @throws IllegalArgumentException if the given string contains characters not in this codec's alphabet
     */
    @Override
    public long decodeStringAsLong(final String string) {
        if (string.length() > this.stringLengthForLongs) {
            throw new IllegalArgumentException(String.format("String \"%s\" is too long to represent a valid long integer.", string));
        }

        final char[] chars = string.toCharArray();
        long decoded = 0;
        int place = chars.length - 1;

        for (final char c : chars) {
            try {
                final long value = this.charactersToValues.get(c);
                decoded += value * this.placeValues[place--];
            } catch (final NullPointerException e) {
                throw new IllegalArgumentException(
                        String.format("Could not decode \"%s\"; character '%s' not in codec alphabet.", string, c));
            }
        }

        return decoded;
    }
}
