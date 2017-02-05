package com.eatthepath.idobfuscator;

import static org.junit.Assert.*;

import org.junit.Test;

public class OffsetIntegerObfuscatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testOffsetIntegerObfuscatorZeroOffset() {
        new OffsetIntegerObfuscator(0);
    }

    @Test
    public void testObfuscateDeobfuscate() {
        for (final int offset : new int[] { 1, 17, -876, Integer.MAX_VALUE, Integer.MIN_VALUE}) {
            final OffsetIntegerObfuscator obfuscator = new OffsetIntegerObfuscator(offset);

            for (final int id : new int[] {0, 12, 284935728, -837, Integer.MAX_VALUE, Integer.MIN_VALUE }) {
                assertEquals(id, obfuscator.deobfuscate(obfuscator.obfuscate(id)));
            }
        }
    }
}
