package com.eatthepath.idobfuscator;

public interface Codec {

    String encodeInteger(int i);

    String encodeLong(long l);

    int decodeInteger(String encodedInteger);

    long decodeLong(String encodedLong);
}
