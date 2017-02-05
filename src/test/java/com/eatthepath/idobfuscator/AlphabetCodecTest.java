package com.eatthepath.idobfuscator;

import static org.junit.Assert.*;

import org.junit.Test;

public class AlphabetCodecTest {

    @Test(expected = NullPointerException.class)
    public void testAlphabetCodecNullAlphabet() {
        new AlphabetCodec(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAlphabetCodecEmptyAlphabet() {
        new AlphabetCodec();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAlphabetCodecShortAlphabet() {
        new AlphabetCodec('a');
    }

    @Test
    public void testEncodeDecodeInteger() {
        final AlphabetCodec codec = new AlphabetCodec('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
        final int[] ids = { 0, 1, 2, 7, 86753, Integer.MAX_VALUE, -77, Integer.MIN_VALUE };

        for (final int id : ids) {
            assertEquals(id, codec.decodeStringAsInteger(codec.encodeIntegerAsString(id)));
        }
    }

    @Test
    public void testExponentiate() {
        assertEquals(1, AlphabetCodec.exponentiate(17, 0));
        assertEquals(17, AlphabetCodec.exponentiate(17, 1));
        assertEquals(289, AlphabetCodec.exponentiate(17, 2));
    }
}
