package com.eatthepath.idobfuscator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AlphabetCodec implements IntegerCodec {

    private final char[] alphabet;
    private final Map<Character, Integer> charactersToValues = new HashMap<>();

    private final int placeValues[];

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
