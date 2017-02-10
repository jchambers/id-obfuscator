package com.eatthepath.idobfuscator;

public class XorIntegerTransformerTest extends IntegerTransformerTest {

    @Override
    public IntegerTransformer[] getTransformers() {
        final long[] masks = new long[] { 0, 1, -7, 77, Long.MAX_VALUE, Long.MIN_VALUE };
        final IntegerTransformer[] transformers = new IntegerTransformer[masks.length];

        for (int i = 0; i < masks.length; i++) {
            transformers[i] = new XorIntegerTransformer(masks[i]);
        }

        return transformers;
    }
}
