package com.eatthepath.idobfuscator;

import static org.junit.Assert.*;

import org.junit.Test;

public class MultiplicativeInverseIntegerObfuscatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testMultiplicativeInverseIntegerObfuscatorZeroMultiplier() {
        new MultiplicativeInverseIntegerObfuscator(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiplicativeInverseIntegerObfuscatorNegativeMultiplier() {
        new MultiplicativeInverseIntegerObfuscator(-77);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiplicativeInverseIntegerObfuscatorEvenMultiplier() {
        new MultiplicativeInverseIntegerObfuscator(862983572);
    }

    @Test
    public void testObfuscateDeobfuscate() {
        final int[] multipliers = { 77, Integer.MAX_VALUE };
        final int[] ids = { 7890, -7458392, Integer.MAX_VALUE, Integer.MIN_VALUE };

        for (final int multiplier : multipliers) {
            for (final int id : ids) {
                final MultiplicativeInverseIntegerObfuscator obfuscator =
                        new MultiplicativeInverseIntegerObfuscator(multiplier);

                assertEquals(id, obfuscator.deobfuscate(obfuscator.obfuscate(id)));
            }
        }
    }
}
