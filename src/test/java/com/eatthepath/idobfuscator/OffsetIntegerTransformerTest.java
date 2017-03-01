package com.eatthepath.idobfuscator;

public class OffsetIntegerTransformerTest extends IntegerTransformerTest {

    @Override
    public IntegerTransformer[] getTransformers() {
        final long[] offsets = new long[] { 1, 17, -876, Long.MAX_VALUE, Long.MIN_VALUE };
        final IntegerTransformer[] transformers = new IntegerTransformer[offsets.length];

        for (int i = 0; i < offsets.length; i++) {
            transformers[i] = new OffsetIntegerTransformer(offsets[i]);
        }

        return transformers;
    }
}
