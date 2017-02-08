package com.eatthepath.idobfuscator;

/**
 * Transforms integers by performing a circular bit shift.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class BitRotationIntegerTransformer implements IntegerTransformer {

    private final int distance;

    /**
     * Constructs a new bit rotation transformer that rotates bits by the given distance. The distance must be between
     * 1 and 31.
     *
     * @param distance the number of places by which to rotate integers
     */
    public BitRotationIntegerTransformer(final int distance) {
        if (distance <= 0 || distance >= Integer.SIZE) {
            throw new IllegalArgumentException(String.format("Rotation distance must be between 0 and %d bits", Integer.SIZE));
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
    public int transformInteger(final int i) {
        return (i << this.distance) | (i >>> (Integer.SIZE - this.distance));
    }

    /**
     * Reverses a bit rotation by shifting its bits to the right by a prescribed distance.
     *
     * @param i the integer to un-rotate
     *
     * @return the original integer
     */
    @Override
    public int reverseTransformInteger(final int i) {
        return (i >>> this.distance) | (i << (Integer.SIZE - this.distance));
    }
}
