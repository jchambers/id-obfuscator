package com.eatthepath.idobfuscator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class IdObfuscatorTest {

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

    @ParameterizedTest
    @MethodSource("argumentsForObfuscateDeobfuscateInteger")
    public void obfuscateDeobfuscateInteger(final long secret, final int i) {
        final IdObfuscator idObfuscator = new IdObfuscator(secret, CODEC);
        assertEquals(i, idObfuscator.deobfuscateInteger(idObfuscator.obfuscateInteger(i)));
    }

    private static Stream<Arguments> argumentsForObfuscateDeobfuscateInteger() {
        return Stream.of(
                arguments(-4783489325329572L, 0),
                arguments(-4783489325329572L, 1),
                arguments(-4783489325329572L, -1),
                arguments(-4783489325329572L, 77),
                arguments(-4783489325329572L, -77),
                arguments(-4783489325329572L, Integer.MAX_VALUE),
                arguments(-4783489325329572L, Integer.MIN_VALUE),
                arguments(4783489325329572L, 0),
                arguments(4783489325329572L, 1),
                arguments(4783489325329572L, -1),
                arguments(4783489325329572L, 77),
                arguments(4783489325329572L, -77),
                arguments(4783489325329572L, Integer.MAX_VALUE),
                arguments(4783489325329572L, Integer.MIN_VALUE),
                arguments(0L, 0),
                arguments(0L, 1),
                arguments(0L, -1),
                arguments(0L, 77),
                arguments(0L, -77),
                arguments(0L, Integer.MAX_VALUE),
                arguments(0L, Integer.MIN_VALUE),
                arguments(Long.MAX_VALUE, 0),
                arguments(Long.MAX_VALUE, 1),
                arguments(Long.MAX_VALUE, -1),
                arguments(Long.MAX_VALUE, 77),
                arguments(Long.MAX_VALUE, -77),
                arguments(Long.MAX_VALUE, Integer.MAX_VALUE),
                arguments(Long.MAX_VALUE, Integer.MIN_VALUE),
                arguments(Long.MIN_VALUE, 0),
                arguments(Long.MIN_VALUE, 1),
                arguments(Long.MIN_VALUE, -1),
                arguments(Long.MIN_VALUE, 77),
                arguments(Long.MIN_VALUE, -77),
                arguments(Long.MIN_VALUE, Integer.MAX_VALUE),
                arguments(Long.MIN_VALUE, Integer.MIN_VALUE)
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsForObfuscateDeobfuscateLong")
    public void obfuscateDeobfuscateLong(final long secret, final long l) {
        final IdObfuscator idObfuscator = new IdObfuscator(secret, CODEC);
        assertEquals(l, idObfuscator.deobfuscateLong(idObfuscator.obfuscateLong(l)));
    }

    private static Stream<Arguments> argumentsForObfuscateDeobfuscateLong() {
        return Stream.of(
                arguments(-4783489325329572L, 0),
                arguments(-4783489325329572L, 1),
                arguments(-4783489325329572L, -1),
                arguments(-4783489325329572L, 77),
                arguments(-4783489325329572L, -77),
                arguments(-4783489325329572L, Long.MAX_VALUE),
                arguments(-4783489325329572L, Long.MIN_VALUE),
                arguments(4783489325329572L, 0),
                arguments(4783489325329572L, 1),
                arguments(4783489325329572L, -1),
                arguments(4783489325329572L, 77),
                arguments(4783489325329572L, -77),
                arguments(4783489325329572L, Long.MAX_VALUE),
                arguments(4783489325329572L, Long.MIN_VALUE),
                arguments(0L, 0),
                arguments(0L, 1),
                arguments(0L, -1),
                arguments(0L, 77),
                arguments(0L, -77),
                arguments(0L, Long.MAX_VALUE),
                arguments(0L, Long.MIN_VALUE),
                arguments(Long.MAX_VALUE, 0),
                arguments(Long.MAX_VALUE, 1),
                arguments(Long.MAX_VALUE, -1),
                arguments(Long.MAX_VALUE, 77),
                arguments(Long.MAX_VALUE, -77),
                arguments(Long.MAX_VALUE, Long.MAX_VALUE),
                arguments(Long.MAX_VALUE, Long.MIN_VALUE),
                arguments(Long.MIN_VALUE, 0),
                arguments(Long.MIN_VALUE, 1),
                arguments(Long.MIN_VALUE, -1),
                arguments(Long.MIN_VALUE, 77),
                arguments(Long.MIN_VALUE, -77),
                arguments(Long.MIN_VALUE, Long.MAX_VALUE),
                arguments(Long.MIN_VALUE, Long.MIN_VALUE)
        );
    }
}