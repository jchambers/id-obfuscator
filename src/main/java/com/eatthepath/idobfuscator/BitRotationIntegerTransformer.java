package com.eatthepath.idobfuscator;

import com.eatthepath.idobfuscator.util.BitwiseOperationUtil;

/**
 * Transforms integers by performing a circular bit shift.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class BitRotationIntegerTransformer implements IntegerTransformer {

    private final int distance;

    /**
     * Constructs a new bit rotation transformer that performs a circular rotation of bits by the given distance.
     *
     * @param distance the number of places by which to rotate integers; must be non-negative
     */
    public BitRotationIntegerTransformer(final int distance) {
        if (distance < 0) {
            throw new IllegalArgumentException("Rotation distance must be non-zero.");
        }

        this.distance = distance;
    }

    /**
     * Transforms the given number by rotating its bits to the left by a prescribed distance.
     *
     * @param i the integer to transform
     *
     * @return the rotated integer
     */
    @Override
    public long transformInteger(final long i, final int nBits) {
        BitwiseOperationUtil.assertValueFitsWithinSize(i, nBits);

        final int effectiveDistance = this.distance % nBits;

        return BitwiseOperationUtil.signExtendLowestBitsToLong(
                (i << effectiveDistance) | (BitwiseOperationUtil.getLowestBits(i, nBits) >>> (nBits - effectiveDistance)), nBits);
    }

    /**
     * Reverses a bit rotation by shifting its bits to the right by a prescribed distance.
     *
     * @param i the integer to un-rotate
     *
     * @return the original integer
     */
    @Override
    public long reverseTransformInteger(final long i, final int nBits) {
        BitwiseOperationUtil.assertValueFitsWithinSize(i, nBits);

        final int effectiveDistance = this.distance % nBits;

        return BitwiseOperationUtil.signExtendLowestBitsToLong(
                (BitwiseOperationUtil.getLowestBits(i, nBits) >>> effectiveDistance) | (i << (nBits - effectiveDistance)), nBits);
    }

    @Override
    public String toString() {
        return String.format("BitRotationIntegerTransformer [distance=%d]", this.distance);
    }
}
