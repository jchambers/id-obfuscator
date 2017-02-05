package com.eatthepath.idobfuscator;

public class XorIntegerTransformer implements IntegerTransformer {

    private final int mask;

    public XorIntegerTransformer(final int mask) {
        this.mask = mask;
    }

    @Override
    public int transform(final int i) {
        return i ^ this.mask;
    }

    @Override
    public int reverseTransform(final int i) {
        return i ^ this.mask;
    }
}
