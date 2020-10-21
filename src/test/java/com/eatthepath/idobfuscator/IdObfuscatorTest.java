package com.eatthepath.idobfuscator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class IdObfuscatorTest {

    private IdObfuscator obfuscator;

    private static final Codec CODEC = new Codec() {

        @Override
        public String encodeInteger(final int i) {
            return String.valueOf(i);
        }

        @Override
        public String encodeLong(final long l) {
            return String.valueOf(l);
        }

        @Override
        public int decodeInteger(final String encodedInteger) {
            return Integer.parseInt(encodedInteger);
        }

        @Override
        public long decodeLong(final String encodedLong) {
            return Long.parseLong(encodedLong);
        }
    };

    @Test
    @Parameters(method = "argumentsForObfuscateDeobfuscateInteger")
    public void obfuscateDeobfuscateInteger(final long secret, final int i) {
        final IdObfuscator idObfuscator = new IdObfuscator(secret, CODEC);
        assertEquals(i, idObfuscator.deobfuscateInteger(idObfuscator.obfuscateInteger(i)));
    }

    private static Object argumentsForObfuscateDeobfuscateInteger() {
        return new Object[] {
                new Object[] { -4783489325329572L, 0 },
                new Object[] { -4783489325329572L, 1 },
                new Object[] { -4783489325329572L, -1 },
                new Object[] { -4783489325329572L, 77 },
                new Object[] { -4783489325329572L, -77 },
                new Object[] { -4783489325329572L, Integer.MAX_VALUE },
                new Object[] { -4783489325329572L, Integer.MIN_VALUE },
                new Object[] { 4783489325329572L, 0 },
                new Object[] { 4783489325329572L, 1 },
                new Object[] { 4783489325329572L, -1 },
                new Object[] { 4783489325329572L, 77 },
                new Object[] { 4783489325329572L, -77 },
                new Object[] { 4783489325329572L, Integer.MAX_VALUE },
                new Object[] { 4783489325329572L, Integer.MIN_VALUE },
                new Object[] { 0L, 0 },
                new Object[] { 0L, 1 },
                new Object[] { 0L, -1 },
                new Object[] { 0L, 77 },
                new Object[] { 0L, -77 },
                new Object[] { 0L, Integer.MAX_VALUE },
                new Object[] { 0L, Integer.MIN_VALUE },
                new Object[] { Long.MAX_VALUE, 0 },
                new Object[] { Long.MAX_VALUE, 1 },
                new Object[] { Long.MAX_VALUE, -1 },
                new Object[] { Long.MAX_VALUE, 77 },
                new Object[] { Long.MAX_VALUE, -77 },
                new Object[] { Long.MAX_VALUE, Integer.MAX_VALUE },
                new Object[] { Long.MAX_VALUE, Integer.MIN_VALUE },
                new Object[] { Long.MIN_VALUE, 0 },
                new Object[] { Long.MIN_VALUE, 1 },
                new Object[] { Long.MIN_VALUE, -1 },
                new Object[] { Long.MIN_VALUE, 77 },
                new Object[] { Long.MIN_VALUE, -77 },
                new Object[] { Long.MIN_VALUE, Integer.MAX_VALUE },
                new Object[] { Long.MIN_VALUE, Integer.MIN_VALUE }
        };
    }

    @Test
    @Parameters(method = "argumentsForObfuscateDeobfuscateLong")
    public void obfuscateDeobfuscateLong(final long secret, final long l) {
        final IdObfuscator idObfuscator = new IdObfuscator(secret, CODEC);
        assertEquals(l, idObfuscator.deobfuscateLong(idObfuscator.obfuscateLong(l)));
    }

    private static Object argumentsForObfuscateDeobfuscateLong() {
        return new Object[] {
                new Object[] { -4783489325329572L, 0 },
                new Object[] { -4783489325329572L, 1 },
                new Object[] { -4783489325329572L, -1 },
                new Object[] { -4783489325329572L, 77 },
                new Object[] { -4783489325329572L, -77 },
                new Object[] { -4783489325329572L, Long.MAX_VALUE },
                new Object[] { -4783489325329572L, Long.MIN_VALUE },
                new Object[] { 4783489325329572L, 0 },
                new Object[] { 4783489325329572L, 1 },
                new Object[] { 4783489325329572L, -1 },
                new Object[] { 4783489325329572L, 77 },
                new Object[] { 4783489325329572L, -77 },
                new Object[] { 4783489325329572L, Long.MAX_VALUE },
                new Object[] { 4783489325329572L, Long.MIN_VALUE },
                new Object[] { 0L, 0 },
                new Object[] { 0L, 1 },
                new Object[] { 0L, -1 },
                new Object[] { 0L, 77 },
                new Object[] { 0L, -77 },
                new Object[] { 0L, Long.MAX_VALUE },
                new Object[] { 0L, Long.MIN_VALUE },
                new Object[] { Long.MAX_VALUE, 0 },
                new Object[] { Long.MAX_VALUE, 1 },
                new Object[] { Long.MAX_VALUE, -1 },
                new Object[] { Long.MAX_VALUE, 77 },
                new Object[] { Long.MAX_VALUE, -77 },
                new Object[] { Long.MAX_VALUE, Long.MAX_VALUE },
                new Object[] { Long.MAX_VALUE, Long.MIN_VALUE },
                new Object[] { Long.MIN_VALUE, 0 },
                new Object[] { Long.MIN_VALUE, 1 },
                new Object[] { Long.MIN_VALUE, -1 },
                new Object[] { Long.MIN_VALUE, 77 },
                new Object[] { Long.MIN_VALUE, -77 },
                new Object[] { Long.MIN_VALUE, Long.MAX_VALUE },
                new Object[] { Long.MIN_VALUE, Long.MIN_VALUE }
        };
    }
}