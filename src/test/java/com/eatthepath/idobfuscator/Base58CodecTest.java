package com.eatthepath.idobfuscator;

import org.junit.Test;

import static org.junit.Assert.*;

public class Base58CodecTest {

    @Test
    public void testEncodeLong() {
        assertEquals("233QC4", new Base58Codec().encodeLong(0x0000287fb4cd));
    }

    @Test
    public void testDecodeLong() {
        assertEquals(0x0000287fb4cd, new Base58Codec().decodeLong("111233QC4"));
    }
}