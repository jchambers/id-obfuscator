package com.eatthepath.idobfuscator;

import com.eatthepath.idobfuscator.util.AlphabetBuilder;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class AlphabetCodecTest {

    @Test
    @Parameters({ "0", "1", "-1", "2147483647", "-2147483648"})
    public void testEncodeDecodeInteger(final int i) {
        final AlphabetCodec codec = new AlphabetCodec(new AlphabetBuilder().includeLowercaseLatinLetters().build());

        assertEquals(i, codec.decodeStringAsInteger(codec.encodeIntegerAsString(i)));
    }

    @Test
    @Parameters({ "0", "1", "-1", "9223372036854775807", "-9223372036854775808"})
    public void testEncodeDecodeLong(final long l) {
        final AlphabetCodec codec = new AlphabetCodec(new AlphabetBuilder().includeLowercaseLatinLetters().build());

        assertEquals(l, codec.decodeStringAsLong(codec.encodeLongAsString(l)));
    }

    @Test(expected = NullPointerException.class)
    public void testAlphabetCodecNullAlphabet() {
        new AlphabetCodec((char[]) null);
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

    @Test(expected = IllegalArgumentException.class)
    public void testDecodeUnexpectedCharacter() {
        new AlphabetCodec('a', 'b', 'c', 'd').decodeStringAsLong("x");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecodeLongString() {
        final char[] alphabet = new AlphabetBuilder()
                .includeUppercaseLatinLetters()
                .includeLowercaseLatinLetters()
                .includeAdditionalCharacters(' ', ',', '.')
                .build();

        new AlphabetCodec(alphabet).decodeStringAsLong(
                "Even though this string contains legal characters, it is too long to represent a valid integer.");
    }
}
