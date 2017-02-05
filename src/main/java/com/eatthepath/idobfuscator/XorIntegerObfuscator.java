package com.eatthepath.idobfuscator;

public class XorIntegerObfuscator implements IntegerObfuscator {

    private final int mask;

    public XorIntegerObfuscator(final int mask) {
        this.mask = mask;
    }

    @Override
    public int obfuscate(final int i) {
        return i ^ this.mask;
    }

    @Override
    public int deobfuscate(final int i) {
        return i ^ this.mask;
    }
}
