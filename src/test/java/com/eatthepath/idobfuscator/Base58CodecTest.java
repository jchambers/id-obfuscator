package com.eatthepath.idobfuscator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Base58CodecTest {

    @ParameterizedTest
    @MethodSource("argumentsForTestEncodeDecodeLong")
    void testEncodeDecodeLong(final long l) {
        final Base58Codec base58Codec = new Base58Codec();
        assertEquals(l, base58Codec.decodeLong(base58Codec.encodeLong(l)));
    }

    private static Stream<Long> argumentsForTestEncodeDecodeLong() {
        return Stream.of(Long.MIN_VALUE, Long.MAX_VALUE, 0L, 1L, -1L, 238947L, -1287352389740L);
    }

    @ParameterizedTest
    @MethodSource("argumentsForTestEncodeDecodeInt")
    void testEncodeDecodeInt(final int i) {
        final Base58Codec base58Codec = new Base58Codec();
        assertEquals(i, base58Codec.decodeInteger(base58Codec.encodeLong(i)));
    }

    private static Stream<Integer> argumentsForTestEncodeDecodeInt() {
        return Stream.of(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 1, -1, 238947, -1287352389);
    }

    @Test
    public void testEncode() {
        final Base58Codec codec = new Base58Codec();

        assertEquals("2NEpo7TZRRrLZSi2U", codec.encode("Hello World!".getBytes(StandardCharsets.UTF_8)));
        assertEquals("USm3fpXnKG5EUBx2ndxBDMPVciP5hGey2Jh4NDv6gmeo1LkMeiKrLJUUBk6Z", codec.encode("The quick brown fox jumps over the lazy dog.".getBytes(StandardCharsets.UTF_8)));
        assertEquals("11233QC4", codec.encode(new byte[] { 0x00, 0x00, 0x28, 0x7f, (byte) 0xb4, (byte) 0xcd }));
    }
}