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
public class AlphabetCodec implements IntegerCodec {

    private final char[] alphabet;
    private final Map<Character, Integer> charactersToValues = new HashMap<>();

    private final int placeValues[];

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
        final int maxStringLength = (int) Math.ceil(Math.log(Math.pow(2, Integer.SIZE)) / Math.log(this.alphabet.length));

        this.placeValues = new int[maxStringLength];
        this.placeValues[0] = 1;

        for (int i = 1; i < maxStringLength; i++) {
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
        final String encodedString;

        if (i == 0) {
            encodedString = new String(new char[] { this.alphabet[0] });
        } else {
            long workingCopy = i & 0xffffffffL;
            final StringBuilder builder = new StringBuilder();

            while (workingCopy != 0) {
                builder.insert(0, this.alphabet[(int) (workingCopy % this.alphabet.length)]);
                workingCopy /= this.alphabet.length;
            }

            encodedString = builder.toString();
        }

        return encodedString;
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
        if (string.length() > this.placeValues.length) {
            throw new IllegalArgumentException(String.format("String \"%s\" is too long to represent a valid integer.", string));
        }

        final char[] chars = string.toCharArray();
        long decoded = 0;
        int exponent = chars.length - 1;

        for (final char c : chars) {
            try {
                final int x = this.charactersToValues.get(c);
                decoded += x * this.placeValues[exponent--];
            } catch (final NullPointerException e) {
                throw new IllegalArgumentException(String.format("Could not decode \"%s\"; character '%s' not in codec alphabet.",
                        string, c));
            }
        }

        return (int) decoded;
    }
}
