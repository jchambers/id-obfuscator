package com.eatthepath.idobfuscator;

public interface IntegerCodec {
    String encodeIntegerAsString(int i);

    int decodeStringAsInteger(String string);
}
