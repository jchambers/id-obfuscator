package com.eatthepath.idobfuscator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BitRotationLongTransformerTest extends LongTransformerTest {

    @Test
    public void testBitRotationLongTransformer() {
        for (int i = 0; i < Long.SIZE - 1; i++) {
            assertEquals(i, new BitRotationLongTransformer(i).getEffectiveDistance());
        }

        for (int i = -1; i > -Long.SIZE; i--) {
            assertEquals(i + Long.SIZE, new BitRotationLongTransformer(i).getEffectiveDistance());
        }

        assertEquals(3, new BitRotationLongTransformer(Long.SIZE + 3).getEffectiveDistance());
    }

    @Override
    public LongTransformer[] getTransformers() {
        return new BitRotationLongTransformer[] {
                new BitRotationLongTransformer(17),
                new BitRotationLongTransformer(-17),
                new BitRotationLongTransformer(0),
                new BitRotationLongTransformer(31),
                new BitRotationLongTransformer(32),
                new BitRotationLongTransformer(65)
        };
    }
}
