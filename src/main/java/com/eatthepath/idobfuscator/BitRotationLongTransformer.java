package com.eatthepath.idobfuscator;

/**
 * Transforms integers by performing a circular bit shift.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class BitRotationLongTransformer implements LongTransformer {
    private final int distance;

    /**
     * Constructs a new bit rotation transformer that rotates bits by the given distance. The distance must be between
     * 1 and 31.
     *
     * @param distance the number of places by which to rotate integers
     */
    public BitRotationLongTransformer(final int distance) {
        if (distance <= 0 || distance >= Long.SIZE) {
            throw new IllegalArgumentException(String.format("Rotation distance must be between 0 and %d bits", Long.SIZE));
        }

        this.distance = distance;
    }

    /**
     * Transforms the given number by rotating its bits to the left by a prescribed distance.
     *
     * @param l the integer to transform
     *
     * @return the rotated integer
     */
    @Override
    public long transformLong(final long l) {
        return (l << this.distance) | (l >>> (Long.SIZE - this.distance));
    }

    /**
     * Reverses a bit rotation by shifting its bits to the right by a prescribed distance.
     *
     * @param l the integer to un-rotate
     *
     * @return the original integer
     */
    @Override
    public long reverseTransformLong(final long l) {
        return (l >>> this.distance) | (l << (Long.SIZE - this.distance));
    }}
