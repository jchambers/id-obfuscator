package com.eatthepath.idobfuscator;

import java.util.Arrays;

class Base58Codec {

    private static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final int[] PLACE_VALUES = new int['z' + 1];

    static {
        Arrays.fill(PLACE_VALUES, -1);

        for (int i = 0; i < ALPHABET.length; i++) {
            PLACE_VALUES[ALPHABET[i]] = i;
        }
    }

    public String encodeInteger(final int i) {
        final byte[] intBytes = new byte[4];

        intBytes[0] = (byte) ((i & 0xff000000) >> 24);
        intBytes[1] = (byte) ((i & 0x00ff0000) >> 16);
        intBytes[2] = (byte) ((i & 0x0000ff00) >> 8);
        intBytes[3] = (byte)  (i & 0x000000ff);

        return encode(intBytes);
    }

    public String encodeLong(long l) {
        final byte[] longBytes = new byte[8];

        longBytes[0] = (byte) ((l & 0xff00000000000000L) >> 56);
        longBytes[1] = (byte) ((l & 0x00ff000000000000L) >> 48);
        longBytes[2] = (byte) ((l & 0x0000ff0000000000L) >> 40);
        longBytes[3] = (byte) ((l & 0x000000ff00000000L) >> 32);
        longBytes[4] = (byte) ((l & 0x00000000ff000000L) >> 24);
        longBytes[5] = (byte) ((l & 0x0000000000ff0000L) >> 16);
        longBytes[6] = (byte) ((l & 0x000000000000ff00L) >> 8);
        longBytes[7] = (byte)  (l & 0x00000000000000ffL);

        return encode(longBytes);
    }

    String encode(final byte[] bytes) {
        // Adapted from https://github.com/bitcoin/libbase58/blob/master/base58.c
        final StringBuilder stringBuilder = new StringBuilder();

        int leadingZeroes = 0;

        for (final byte b : bytes) {
            if (b == 0) {
                leadingZeroes++;
            } else {
                break;
            }
        }

        final byte[] base58EncodedBytes = new byte[(((bytes.length - leadingZeroes) * 138) / 100) + 1];

        int high = base58EncodedBytes.length - 1;

        for (int i = leadingZeroes; i < bytes.length; i++) {
            int carry = bytes[i] & 0xff;
            int j;

            for (j = base58EncodedBytes.length - 1; j > high || carry != 0; j--) {
                carry += (base58EncodedBytes[j] & 0xff) * 256;
                base58EncodedBytes[j] = (byte) (carry % 58);
                carry /= 58;
            }

            high = j;
        }

        for (int i = 0; i < leadingZeroes; i++) {
            stringBuilder.append(ALPHABET[0]);
        }

        int skip = 0;

        for (final byte b : base58EncodedBytes) {
            if (b == 0) {
                skip++;
            } else {
                break;
            }
        }

        for (int i = skip; i < base58EncodedBytes.length; i++) {
            stringBuilder.append(ALPHABET[base58EncodedBytes[i]]);
        }

        return stringBuilder.toString();
    }

    public int decodeInteger(final String encodedInteger) {
        return (int) decode(encodedInteger);
    }

    public long decodeLong(final String encodedLong) {
        return decode(encodedLong);
    }

    static long decode(final String string) {
        long value = 0;

        for (final char c : string.toCharArray()) {
            if (PLACE_VALUES[c] == -1) {
                throw new IllegalArgumentException("Illegal base58 character: " + c);
            }

            value *= 58;
            value += PLACE_VALUES[c];
        }

        return value;
    }
}
