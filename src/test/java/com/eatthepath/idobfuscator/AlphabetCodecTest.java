package com.eatthepath.idobfuscator;

import org.junit.Test;
import com.eatthepath.idobfuscator.util.AlphabetBuilder;

public class AlphabetCodecTest extends IntegerCodecTest {

    @Override
    protected IntegerCodec[] getCodecs() {
        return new AlphabetCodec[] {  new AlphabetCodec(new AlphabetBuilder().includeLowercaseLatinLetters().build()) };
    }

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

    @Test(expected = IllegalArgumentException.class)
    public void testDecodeUnexpectedCharacter() {
        new AlphabetCodec('a', 'b', 'c', 'd').decodeStringAsInteger("x", Integer.SIZE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecodeLongString() {
        final char[] alphabet = new AlphabetBuilder()
                .includeUppercaseLatinLetters()
                .includeLowercaseLatinLetters()
                .includeAdditionalCharacters(' ', ',', '.')
                .build();

        new AlphabetCodec(alphabet).decodeStringAsInteger(
                "Even though this string contains legal characters, it is too long to represent a valid integer.", Integer.SIZE);
    }
}
