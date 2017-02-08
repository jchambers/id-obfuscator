package com.eatthepath.idobfuscator;

import static org.junit.Assert.*;

import org.junit.Test;

public class XorIntegerTransformerTest {

    @Test
    public void test() {
        for (final int mask : new int[] { 0, 1, -7, 77, Integer.MAX_VALUE, Integer.MIN_VALUE }) {
            final XorIntegerTransformer transformer = new XorIntegerTransformer(mask);

            for (final int id : new int[] { 0, 1, -7, 77, Integer.MAX_VALUE, Integer.MIN_VALUE }) {
                assertEquals(id, transformer.reverseTransformInteger(transformer.reverseTransformInteger(id)));
            }
        }
    }
}
