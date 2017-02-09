package com.eatthepath.idobfuscator;

/**
 * Transforms long integers by adding a given offset, and reverses transformations by subtracting that offset.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class OffsetLongTransformer implements LongTransformer {

    private final long offset;

    /**
     * Constructs a new offset transformer with the given offset. Offsets must be non-zero (otherwise the transformation
     * would be a no-op).
     *
     * @param offset the offset to apply when transforming numbers; must be non-zero
     */
    public OffsetLongTransformer(final long offset) {
        if (offset == 0) {
            throw new IllegalArgumentException("Offset must be non-zero");
        }

        this.offset = offset;
    }

    /**
     * Transforms the given long integer by adding this transformer's offset to that integer.
     *
     * @param l the long integer to offset
     *
     * @return the transformed long integer
     */
    @Override
    public long transformLong(final long l) {
        return l + this.offset;
    }

    /**
     * Reverses an offset transformation by subtracting this transformer's offset from the given long integer.
     *
     * @param l the long integer for which to reverse an offset transformation
     *
     * @return the original long integer
     */
    @Override
    public long reverseTransformLong(final long l) {
        return l - this.offset;
    }

}
