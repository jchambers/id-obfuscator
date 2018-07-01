package com.eatthepath.idobfuscator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BitRotationIntegerTransformerTest extends IntegerTransformerTest {

    @Test
    public void testBitRotationIntegerTransformer() {
        for (int i = 0; i < Integer.SIZE - 1; i++) {
            assertEquals(i, new BitRotationIntegerTransformer(i).getEffectiveDistance());
        }

        for (int i = -1; i > -Integer.SIZE; i--) {
            assertEquals(i + Integer.SIZE, new BitRotationIntegerTransformer(i).getEffectiveDistance());
        }

        assertEquals(3, new BitRotationIntegerTransformer(Integer.SIZE + 3).getEffectiveDistance());
    }

    @Override
    public IntegerTransformer[] getTransformers() {
        return new BitRotationIntegerTransformer[] {
                new BitRotationIntegerTransformer(17),
                new BitRotationIntegerTransformer(-17),
                new BitRotationIntegerTransformer(0),
                new BitRotationIntegerTransformer(31),
                new BitRotationIntegerTransformer(32),
                new BitRotationIntegerTransformer(65)
        };
    }
}
