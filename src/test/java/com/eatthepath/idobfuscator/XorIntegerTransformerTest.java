package com.eatthepath.idobfuscator;

public class XorIntegerTransformerTest extends IntegerTransformerTest {

    @Override
    public IntegerTransformer[] getTransformers() {
        final int[] masks = new int[] { 0, 1, -7, 77, Integer.MAX_VALUE, Integer.MIN_VALUE };
        final IntegerTransformer[] transformers = new IntegerTransformer[masks.length];

        for (int i = 0; i < masks.length; i++) {
            transformers[i] = new XorIntegerTransformer(masks[i]);
        }

        return transformers;
    }
}
