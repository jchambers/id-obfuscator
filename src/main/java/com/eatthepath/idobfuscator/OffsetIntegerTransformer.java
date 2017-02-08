package com.eatthepath.idobfuscator;

/**
 * Transforms integers by adding a given offset, and reverses transformations by subtracting that offset.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class OffsetIntegerTransformer implements IntegerTransformer {

    private final int offset;

    /**
     * Constructs a new offset transformer with the given offset. Offsets must be non-zero (otherwise the transformation
     * would be a no-op).
     *
     * @param offset the offset to apply when transforming numbers; must be non-zero
     */
    public OffsetIntegerTransformer(final int offset) {
        if (offset == 0) {
            throw new IllegalArgumentException("Offset must be non-zero");
        }

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
    public int transformInteger(final int i) {
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
    public int reverseTransformInteger(final int i) {
        return i - this.offset;
    }
}
