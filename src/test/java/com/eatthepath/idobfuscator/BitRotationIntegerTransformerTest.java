package com.eatthepath.idobfuscator;

import org.junit.Test;

public class BitRotationIntegerTransformerTest extends IntegerTransformerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testBitRotationIntegerTransformerNegativeDistance() {
        new BitRotationIntegerTransformer(-1);
    }

    @Override
    public IntegerTransformer[] getTransformers() {
        return new BitRotationIntegerTransformer[] { new BitRotationIntegerTransformer(17) };
    }
}
