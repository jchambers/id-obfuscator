package com.eatthepath.idobfuscator;

import java.math.BigInteger;

public class IdObfuscator {

    private final int integerMultiplier;
    private final int integerInverse;
    private final long longMultiplier;
    private final long longInverse;

    private final Codec codec;

    public IdObfuscator(final long secretMultiplier, final Codec codec) {
        this.integerMultiplier = (int) (secretMultiplier | 0x01);
        this.integerInverse = BigInteger.valueOf(this.integerMultiplier).modInverse(BigInteger.ONE.shiftLeft(Integer.SIZE)).intValue();

        this.longMultiplier = secretMultiplier | 0x01;
        this.longInverse = BigInteger.valueOf(this.longMultiplier).modInverse(BigInteger.valueOf(1).shiftLeft(64)).longValue();

        this.codec = codec;
    }

    public String obfuscateInteger(final int i) {
        return this.codec.encodeInteger(i * this.integerMultiplier);
    }

    public int deobfuscateInteger(final String obfuscated) {
        return this.codec.decodeInteger(obfuscated) * this.integerInverse;
    }

    public String obfuscateLong(final long l) {
        return this.codec.encodeLong(l * this.longMultiplier);
    }

    public long deobfuscateLong(final String obfuscated) {
        return this.codec.decodeLong(obfuscated) * this.longInverse;
    }
}
