package com.eatthepath.idobfuscator;

import org.junit.Test;

public class MultiplicativeInverseIntegerTransformerTest extends IntegerTransformerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testMultiplicativeInverseIntegerTransformerZeroMultiplier() {
        new MultiplicativeInverseIntegerTransformer(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiplicativeInverseIntegerTransformerNegativeMultiplier() {
        new MultiplicativeInverseIntegerTransformer(-77);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiplicativeInverseIntegerTransformerEvenMultiplier() {
        new MultiplicativeInverseIntegerTransformer(862983572);
    }

    @Override
    public IntegerTransformer[] getTransformers() {
        final long[] multipliers = new long[] { 77, Integer.MAX_VALUE };
        final IntegerTransformer[] transformers = new MultiplicativeInverseIntegerTransformer[multipliers.length];

        for (int i = 0; i < multipliers.length; i++) {
            transformers[i] = new MultiplicativeInverseIntegerTransformer(multipliers[i]);
        }

        return transformers;
    }
}
