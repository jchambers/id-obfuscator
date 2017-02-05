package com.eatthepath.idobfuscator;

import static org.junit.Assert.*;

import org.junit.Test;

public class XorIntegerObfuscatorTest {

    @Test
    public void test() {
        for (final int mask : new int[] { 0, 1, -7, 77, Integer.MAX_VALUE, Integer.MIN_VALUE }) {
            final XorIntegerObfuscator obfuscator = new XorIntegerObfuscator(mask);

            for (final int id : new int[] { 0, 1, -7, 77, Integer.MAX_VALUE, Integer.MIN_VALUE }) {
                assertEquals(id, obfuscator.deobfuscate(obfuscator.deobfuscate(id)));
            }
        }
    }
}
