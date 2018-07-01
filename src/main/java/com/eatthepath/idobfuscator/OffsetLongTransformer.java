package com.eatthepath.idobfuscator;

public class OffsetLongTransformer implements LongTransformer {

    private final long offset;

    /**
     * Constructs a new offset transformer with the given offset. Offsets should be non-zero (otherwise the
     * transformation would be a no-op), but zero offsets are technically allowed.
     *
     * @param offset the offset to apply when transforming numbers
     */
    public OffsetLongTransformer(final long offset) {
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
    public long transformLong(final long i) {
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
    public long reverseTransformLong(final long i) {
        return i - this.offset;
    }

    @Override
    public String toString() {
        return String.format("OffsetLongTransformer [offset=%d]", this.offset);
    }
}
