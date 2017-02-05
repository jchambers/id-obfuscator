package com.eatthepath.idobfuscator;

import static org.junit.Assert.*;

import org.junit.Test;

public class OffsetIntegerTransformerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testOffsetIntegerObfuscatorZeroOffset() {
        new OffsetIntegerTransformer(0);
    }

    @Test
    public void testTransformReverseTransform() {
        for (final int offset : new int[] { 1, 17, -876, Integer.MAX_VALUE, Integer.MIN_VALUE}) {
            final OffsetIntegerTransformer transformer = new OffsetIntegerTransformer(offset);

            for (final int id : new int[] {0, 12, 284935728, -837, Integer.MAX_VALUE, Integer.MIN_VALUE }) {
                assertEquals(id, transformer.reverseTransform(transformer.transform(id)));
            }
        }
    }
}
