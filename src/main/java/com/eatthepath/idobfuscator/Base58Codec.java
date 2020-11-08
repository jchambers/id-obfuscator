package com.eatthepath.idobfuscator;

import java.util.Arrays;

public class Base58Codec implements Codec {

    private static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final int[] PLACE_VALUES = new int['z' + 1];

    static {
        Arrays.fill(PLACE_VALUES, -1);

        for (int i = 0; i < ALPHABET.length; i++) {
            PLACE_VALUES[ALPHABET[i]] = i;
        }
    }

    @Override
    public String encodeInteger(final int i) {
        return encodeLong(i);
    }

    @Override
    public String encodeLong(long l) {
        final char[] encoded = new char[11];
        int writeIndex = 0;

        while (l != 0) {
            encoded[encoded.length - writeIndex++ - 1] = ALPHABET[(int) Long.remainderUnsigned(l, 58)];
            l = Long.divideUnsigned(l, 58);
        }

        return new String(encoded, encoded.length - writeIndex, writeIndex);
    }

    @Override
    public int decodeInteger(final String encodedInteger) {
        return (int) decode(encodedInteger);
    }

    @Override
    public long decodeLong(final String encodedLong) {
        return decode(encodedLong);
    }

    static long decode(final String string) {
        long value = 0;

        for (final char c : string.toCharArray()) {
            value *= 58;
            value += PLACE_VALUES[c];
        }

        return value;
    }
}
