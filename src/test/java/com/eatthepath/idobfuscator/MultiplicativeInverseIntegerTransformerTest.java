package com.eatthepath.idobfuscator;

import static org.junit.Assert.*;

import org.junit.Test;

public class MultiplicativeInverseIntegerTransformerTest {

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

    @Test
    public void testObfuscateDeobfuscate() {
        final int[] multipliers = { 77, Integer.MAX_VALUE };
        final int[] ids = { 7890, -7458392, Integer.MAX_VALUE, Integer.MIN_VALUE };

        for (final int multiplier : multipliers) {
            for (final int id : ids) {
                final MultiplicativeInverseIntegerTransformer transformer =
                        new MultiplicativeInverseIntegerTransformer(multiplier);

                assertEquals(id, transformer.reverseTransformInteger(transformer.transformInteger(id)));
            }
        }
    }
}
