package com.eatthepath.idobfuscator;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eatthepath.idobfuscator.util.AlphabetBuilder;

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

    @Test(expected = IllegalArgumentException.class)
    public void testAlphabetCodecRepeatedCharacters() {
        new AlphabetCodec('a', 'b', 'c', 'd', 'c');
    }

    @Test
    public void testEncodeDecodeInteger() {
        final AlphabetCodec codec = new AlphabetCodec(new AlphabetBuilder().includeLowercaseLatinLetters().build());
        final int[] ids = { 0, 1, 2, 7, 86753, Integer.MAX_VALUE, -77, Integer.MIN_VALUE };

        for (final int id : ids) {
            assertEquals(id, codec.decodeStringAsInteger(codec.encodeIntegerAsString(id)));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecodeUnexpectedCharacter() {
        new AlphabetCodec('a', 'b', 'c', 'd').decodeStringAsInteger("x");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecodeLongString() {
        final char[] alphabet = new AlphabetBuilder()
                .includeUppercaseLatinLetters()
                .includeLowercaseLatinLetters()
                .includeAdditionalCharacters(' ', ',', '.')
                .build();

        new AlphabetCodec(alphabet).decodeStringAsInteger(
                "Even though this string contains legal characters, it is too long to represent a valid integer.");
    }
}
