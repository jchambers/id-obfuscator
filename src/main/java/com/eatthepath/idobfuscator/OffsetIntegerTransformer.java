package com.eatthepath.idobfuscator;

/**
 * Transforms integers by adding a given offset, and reverses transformations by subtracting that offset.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class OffsetIntegerTransformer implements IntegerTransformer {

    private final long offset;

    /**
     * Constructs a new offset transformer with the given offset. Offsets should be non-zero (otherwise the
     * transformation would be a no-op), but zero offsets are technically allowed.
     *
     * @param offset the offset to apply when transforming numbers
     */
    public OffsetIntegerTransformer(final long offset) {
        this.offset = offset;
    }

    /**
     * Transforms the given integer by adding this transformer's offset to that integer.
     *
     * @param i the integer to offset
     *
     * @return the transformed integer
     */
    @Override
    public long transformInteger(final long i) {
        return i + this.offset;
    }

    /**
     * Reverses an offset transformation by subtracting this transformer's offset from the given integer.
     *
     * @param i the integer for which to reverse an offset transformation
     *
     * @return the original integer
     */
    @Override
    public long reverseTransformInteger(final long i) {
        return i - this.offset;
    }

    @Override
    public String toString() {
        return String.format("OffsetIntegerTransformer [offset=%d]", this.offset);
    }
}
