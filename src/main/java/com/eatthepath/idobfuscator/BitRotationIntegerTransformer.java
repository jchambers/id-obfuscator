package com.eatthepath.idobfuscator;

/**
 * Transforms integers by performing a circular bit shift.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class BitRotationIntegerTransformer implements IntegerTransformer {

    private final int originalDistance;
    private transient final int effectiveDistance;

    /**
     * Constructs a new bit rotation transformer that performs a circular rotation of bits by the given distance.
     *
     * @param distance the number of places by which to rotate integers
     */
    public BitRotationIntegerTransformer(final int distance) {
        this.originalDistance = distance;

        // Normalize the rotation distance to the range of [0, 64).
        this.effectiveDistance = (distance % Long.SIZE) + (distance < 0 ? Long.SIZE : 0);
    }

    int getEffectiveDistance() {
        return this.effectiveDistance;
    }

    /**
     * Transforms the given number by rotating its bits to the left by a prescribed distance.
     *
     * @param i the integer to transform
     *
     * @return the rotated integer
     */
    @Override
    public long transformInteger(final long i) {
        return (i << this.effectiveDistance) | (i >>> (Long.SIZE - this.effectiveDistance));
    }

    /**
     * Reverses a bit rotation by shifting its bits to the right by a prescribed distance.
     *
     * @param i the integer to un-rotate
     *
     * @return the original integer
     */
    @Override
    public long reverseTransformInteger(final long i) {
        return (i >>> this.effectiveDistance) | (i << (Long.SIZE -this.effectiveDistance));
    }

    @Override
    public String toString() {
        return String.format("BitRotationIntegerTransformer [distance=%d]", this.originalDistance);
    }
}
