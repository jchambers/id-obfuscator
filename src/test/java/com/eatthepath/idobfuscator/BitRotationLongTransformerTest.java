package com.eatthepath.idobfuscator;

import static org.junit.Assert.*;

import org.junit.Test;

public class BitRotationLongTransformerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testBitRotationLongTransformerZeroDistance() {
        new BitRotationLongTransformer(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBitRotationLongTransformerNegativeDistance() {
        new BitRotationLongTransformer(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBitRotationLongTransformerExcessiveDistance() {
        new BitRotationLongTransformer(Long.SIZE);
    }

    @Test
    public void testObfuscateDeobfuscate() {
        final BitRotationLongTransformer transformer = new BitRotationLongTransformer(17);

        for (final long id : new long[] { 0, 1, 2, 62893752, -77, Long.MAX_VALUE, Long.MIN_VALUE }) {
            assertEquals(id, transformer.reverseTransformLong(transformer.transformLong(id)));
        }
    }
}

