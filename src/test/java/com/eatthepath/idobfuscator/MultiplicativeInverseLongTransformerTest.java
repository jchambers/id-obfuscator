package com.eatthepath.idobfuscator;

import org.junit.Test;

public class MultiplicativeInverseLongTransformerTest extends LongTransformerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testMultiplicativeInverseLongTransformerZeroMultiplier() {
        new MultiplicativeInverseLongTransformer(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiplicativeInverseLongTransformerNegativeMultiplier() {
        new MultiplicativeInverseLongTransformer(-77);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiplicativeInverseLongTransformerEvenMultiplier() {
        new MultiplicativeInverseLongTransformer(862983572);
    }

    @Override
    public LongTransformer[] getTransformers() {
        final long[] multipliers = new long[] { 77, Long.MAX_VALUE };
        final LongTransformer[] transformers = new MultiplicativeInverseLongTransformer[multipliers.length];

        for (int i = 0; i < multipliers.length; i++) {
            transformers[i] = new MultiplicativeInverseLongTransformer(multipliers[i]);
        }

        return transformers;
    }
}
