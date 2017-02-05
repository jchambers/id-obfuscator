package com.eatthepath.idobfuscator;

public class OffsetIntegerObfuscator implements IntegerObfuscator {

    private final int offset;

    public OffsetIntegerObfuscator(final int offset) {
        if (offset == 0) {
            throw new IllegalArgumentException("Offset must be non-zero");
        }

        this.offset = offset;
    }

    @Override
    public int obfuscate(final int i) {
        return i + this.offset;
    }

    @Override
    public int deobfuscate(final int i) {
        return i - this.offset;
    }
}
