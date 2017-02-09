package com.eatthepath.idobfuscator;

import static org.junit.Assert.*;

import org.junit.Test;

public class OffsetLongTransformerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testOffsetLongObfuscatorZeroOffset() {
        new OffsetLongTransformer(0);
    }

    @Test
    public void testTransformReverseTransform() {
        for (final long offset : new long[] { 1, 17, -876, Long.MAX_VALUE, Long.MIN_VALUE}) {
            final OffsetLongTransformer transformer = new OffsetLongTransformer(offset);

            for (final long id : new long[] {0, 12, 284935728, -837, Long.MAX_VALUE, Long.MIN_VALUE }) {
                assertEquals(id, transformer.reverseTransformLong(transformer.transformLong(id)));
            }
        }
    }
}
