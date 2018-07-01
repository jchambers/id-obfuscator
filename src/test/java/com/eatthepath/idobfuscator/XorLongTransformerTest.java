package com.eatthepath.idobfuscator;

public class XorLongTransformerTest extends LongTransformerTest {

    @Override
    public LongTransformer[] getTransformers() {
        final long[] masks = new long[] { 0, 1, -7, 77, Long.MAX_VALUE, Long.MIN_VALUE };
        final LongTransformer[] transformers = new LongTransformer[masks.length];

        for (int i = 0; i < masks.length; i++) {
            transformers[i] = new XorLongTransformer(masks[i]);
        }

        return transformers;
    }
}
