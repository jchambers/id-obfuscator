package com.eatthepath.idobfuscator;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class Base58CodecTest {

    @Test
    public void testEncodeLong() {
        assertEquals("1111233QC4", new Base58Codec().encodeLong(0x00000000287fb4cd));
    }

    @Test
    public void testDecodeLong() {
        assertEquals(0x00000000287fb4cd, new Base58Codec().decodeLong("11233QC4"));
    }

    @Test
    public void testEncode() {
        final Base58Codec codec = new Base58Codec();

        assertEquals("2NEpo7TZRRrLZSi2U", codec.encode("Hello World!".getBytes(StandardCharsets.UTF_8)));
        assertEquals("USm3fpXnKG5EUBx2ndxBDMPVciP5hGey2Jh4NDv6gmeo1LkMeiKrLJUUBk6Z", codec.encode("The quick brown fox jumps over the lazy dog.".getBytes(StandardCharsets.UTF_8)));
        assertEquals("11233QC4", codec.encode(new byte[] { 0x00, 0x00, 0x28, 0x7f, (byte) 0xb4, (byte) 0xcd }));
    }
}