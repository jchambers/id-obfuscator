package com.eatthepath.idobfuscator;

import static org.junit.Assert.*;

import org.junit.Test;

public class BitRotationIntegerObfuscatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testBitRotationIntegerObfuscatorZeroDistance() {
        new BitRotationIntegerObfuscator(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBitRotationIntegerObfuscatorNegativeDistance() {
        new BitRotationIntegerObfuscator(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBitRotationIntegerObfuscatorExcessiveDistance() {
        new BitRotationIntegerObfuscator(Integer.SIZE);
    }

    @Test
    public void testObfuscateDeobfuscate() {
        final BitRotationIntegerObfuscator obfuscator = new BitRotationIntegerObfuscator(17);

        for (final int id : new int[] { 0, 1, 2, 62893752, -77, Integer.MAX_VALUE, Integer.MIN_VALUE }) {
            assertEquals(id, obfuscator.deobfuscate(obfuscator.obfuscate(id)));
        }
    }
}
