package com.eatthepath.idobfuscator;

/**
 * Transforms integers by performing a circular bit shift.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class BitRotationIntegerTransformer implements IntegerTransformer {

    private final long originalDistance;
    private transient final int effectiveDistance;

    /**
     * Constructs a new bit rotation transformer that performs a circular rotation of bits by the given distance.
     *
     * @param distance the number of places by which to rotate integers
     */
    public BitRotationIntegerTransformer(final int distance) {
        this.originalDistance = distance;

        // Normalize the rotation distance to the range of [0, 32).
        this.effectiveDistance = (int) ((distance % Integer.SIZE) + (distance < 0 ? Integer.SIZE : 0));
    }

    int getEffectiveDistance() {
        return this.effectiveDistance;
    }

    /**
     * Transforms the given integer by rotating its bits to the left by a prescribed distance.
     *
     * @param i the integer to transform
     *
     * @return the rotated integer
     */
    @Override
    public int transformInteger(final int i) {
        return (i << this.effectiveDistance) | (i >>> (Integer.SIZE - this.effectiveDistance));
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
        return (i >>> this.effectiveDistance) | (i << (Integer.SIZE - this.effectiveDistance));
    }

    @Override
    public String toString() {
        return String.format("BitRotationIntegerTransformer [distance=%d]", this.originalDistance);
    }
}
