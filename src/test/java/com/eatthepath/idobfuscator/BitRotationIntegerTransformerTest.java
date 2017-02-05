package com.eatthepath.idobfuscator;

import static org.junit.Assert.*;

import org.junit.Test;

public class BitRotationIntegerTransformerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testBitRotationIntegerTransformerZeroDistance() {
        new BitRotationIntegerTransformer(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBitRotationIntegerTransformerNegativeDistance() {
        new BitRotationIntegerTransformer(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBitRotationIntegerTransformerExcessiveDistance() {
        new BitRotationIntegerTransformer(Integer.SIZE);
    }

    @Test
    public void testObfuscateDeobfuscate() {
        final BitRotationIntegerTransformer transformer = new BitRotationIntegerTransformer(17);

        for (final int id : new int[] { 0, 1, 2, 62893752, -77, Integer.MAX_VALUE, Integer.MIN_VALUE }) {
            assertEquals(id, transformer.reverseTransform(transformer.transform(id)));
        }
    }
}
