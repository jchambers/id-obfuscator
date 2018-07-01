package com.eatthepath.idobfuscator;

/**
 * Transforms long integers by performing a circular bit shift.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class BitRotationLongTransformer implements LongTransformer {

    private final long originalDistance;
    private transient final int effectiveDistance;

    /**
     * Constructs a new bit rotation transformer that performs a circular rotation of bits by the given distance.
     *
     * @param distance the number of places by which to rotate integers
     */
    public BitRotationLongTransformer(final int distance) {
        this.originalDistance = distance;

        // Normalize the rotation distance to the range of [0, 64).
        this.effectiveDistance = (int) ((distance % Long.SIZE) + (distance < 0 ? Long.SIZE : 0));
    }

    int getEffectiveDistance() {
        return this.effectiveDistance;
    }

    /**
     * Transforms the given long integer by rotating its bits to the left by a prescribed distance.
     *
     * @param l the long integer to transform
     *
     * @return the rotated long integer
     */
    @Override
    public long transformLong(final long l) {
        return (l << this.effectiveDistance) | (l >>> (Long.SIZE - this.effectiveDistance));
    }

    /**
     * Reverses a bit rotation by shifting its bits to the right by a prescribed distance.
     *
     * @param l the long integer to un-rotate
     *
     * @return the original long integer
     */
    @Override
    public long reverseTransformLong(final long l) {
        return (l >>> this.effectiveDistance) | (l << (Long.SIZE - this.effectiveDistance));
    }

    @Override
    public String toString() {
        return String.format("BitRotationIntegerTransformer [distance=%d]", this.originalDistance);
    }
}
