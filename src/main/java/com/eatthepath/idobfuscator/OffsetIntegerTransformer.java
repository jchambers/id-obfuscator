package com.eatthepath.idobfuscator;

public class OffsetIntegerTransformer implements IntegerTransformer {

    private final int offset;

    public OffsetIntegerTransformer(final int offset) {
        if (offset == 0) {
            throw new IllegalArgumentException("Offset must be non-zero");
        }

        this.offset = offset;
    }

    @Override
    public int transform(final int i) {
        return i + this.offset;
    }

    @Override
    public int reverseTransform(final int i) {
        return i - this.offset;
    }
}
