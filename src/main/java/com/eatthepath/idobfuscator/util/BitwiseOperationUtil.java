package com.eatthepath.idobfuscator.util;

/**
 * A utility class for performing bitwise operations commonly needed for masking and extending 64-bit (long) integers.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class BitwiseOperationUtil {
    private static final long[] MASKS;

    private static final long[] MAX_VALUES;
    private static final long[] MIN_VALUES;

    static {
        MASKS = new long[Long.SIZE + 1];

        MAX_VALUES = new long[Long.SIZE + 1];
        MIN_VALUES = new long[Long.SIZE + 1];

        MASKS[0] = 0;
        MAX_VALUES[0] = 0;
        MIN_VALUES[0] = 0;

        for (int i = 1; i < MASKS.length - 1; i++) {
            MASKS[i] = (1L << i) - 1;

            MAX_VALUES[i] = (1L << (i - 1)) - 1;
            MIN_VALUES[i] = -(1L << (i - 1));
        }

        MASKS[Long.SIZE] = -1L;
        MAX_VALUES[Long.SIZE] = Long.MAX_VALUE;
        MIN_VALUES[Long.SIZE] = Long.MIN_VALUE;
    }

    /**
     * Returns a 64-bit (long) integer with all but the lowest {@code nBits} bits of the given integer set to zero. The
     * lowest {@code nBits} bits are unchanged.
     *
     * @param value the integer from which to extract the lowest {@code nBits} bits
     * @param nBits the number of bits to extract from the given integer
     *
     * @return a new integer with the lowest {@code nBits} bits of the original {@code value} preserved, and the
     * remaining high bits set to zero
     */
    public static long getLowestBits(final long value, final int nBits) {
        return value & MASKS[nBits];
    }

    /**
     * Returns a 64-bit (long) integer that represents the same sign-extended value of the lowest {@code nBits} bits of
     * the given {@code value} in 64-bit form.
     *
     * @param value the value from which to sign-extend the lowest {@code nBits} bits
     * @param nBits the number of bits from which to extract a sign-extended value
     *
     * @return a sign-extended, 64-bit version of the lowest {@code nBits} bits of the given {@code value}
     */
    public static long signExtendLowestBitsToLong(final long value, final int nBits) {
        if (nBits < 1 || nBits > Long.SIZE) {
            throw new IllegalArgumentException("Number of bits must be a positive integer less than or equal to 64.");
        }

        final int distance = Long.SIZE - nBits;
        return (value << distance) >> distance;
    }

    /**
     * Returns the largest (i.e. most positive) integer value that can be expressed with the given number of bits.
     *
     * @param nBits the number of bits to be used to express an integer; must be between 1 and 64, inclusive
     *
     * @return the largest integer value that can be expressed with the given number of bits
     *
     * @throws IllegalArgumentException if the given number of bits does not fall between 1 and 64, inclusive
     */
    public static long getMaxValueForSize(final int nBits) {
        if (nBits < 1 || nBits > 64) {
            throw new IllegalArgumentException(String.format("Number of bits (%d) must be beween 1 and 64, inclusive.", nBits));
        }

        return MAX_VALUES[nBits];
    }

    /**
     * Returns the smallest (i.e. most negative) integer value that can be expressed with the given number of bits.
     *
     * @param nBits the number of bits to be used to express an integer; must be between 1 and 64, inclusive
     *
     * @return the smallest (i.e. most negative) integer value that can be expressed with the given number of bits
     *
     * @throws IllegalArgumentException if the given number of bits does not fall between 1 and 64, inclusive
     */
    public static long getMinValueForSize(final int nBits) {
        if (nBits < 1 || nBits > 64) {
            throw new IllegalArgumentException(String.format("Number of bits (%d) must be beween 1 and 64, inclusive.", nBits));
        }

        return MIN_VALUES[nBits];
    }

    /**
     * Checks that the given value can be represented using the given number of bits. This method throws an
     * {@link IllegalArgumentException} if the given value is greater than the maximum value for the given number of
     * bits, if the given value is less than the minimum value for the given number of bits, or if the number of bits
     * does not fall between 1 and 64, inclusive.
     *
     * @param value the value to check
     * @param nBits the number of bits in which the given value must fit; must be between 1 and 64, inclusive
     *
     * @throws IllegalArgumentException if the given value cannot be expressed with the given number of bits, or if the
     * given number of bits does not fall between 1 and 64, inclusive
     */
    public static void assertValueFitsWithinSize(final long value, final int nBits) {
        if (nBits < 1 || nBits > 64) {
            throw new IllegalArgumentException(String.format("Number of bits (%d) must be beween 1 and 64, inclusive.", nBits));
        } else if (value > MAX_VALUES[nBits] || value < MIN_VALUES[nBits]) {
            throw new IllegalArgumentException(String.format("Value %d does not fit within %d bits; valid values range from %d to %d, inclusive.",
                    value, nBits, MIN_VALUES[nBits], MAX_VALUES[nBits]));
        }
    }
}
