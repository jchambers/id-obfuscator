package com.eatthepath.idobfuscator;

public class OffsetLongTransformerTest extends LongTransformerTest {

    @Override
    public LongTransformer[] getTransformers() {
        final long[] offsets = new long[] { 1, 17, -876, Long.MAX_VALUE, Long.MIN_VALUE };
        final LongTransformer[] transformers = new LongTransformer[offsets.length];

        for (int i = 0; i < offsets.length; i++) {
            transformers[i] = new OffsetLongTransformer(offsets[i]);
        }

        return transformers;
    }
}
