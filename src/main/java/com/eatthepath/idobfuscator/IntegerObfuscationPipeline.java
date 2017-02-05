package com.eatthepath.idobfuscator;

public class IntegerObfuscationPipeline {
    private final IntegerObfuscator[] obfuscators;
    private final IntegerCodec codec;

    public IntegerObfuscationPipeline(final IntegerCodec codec, final IntegerObfuscator... obfuscators) {
        this.obfuscators = obfuscators;
        this.codec = codec;
    }

    public String obuscate(final int i) {
        int encodedInteger = i;

        for (final IntegerObfuscator obfuscator : this.obfuscators) {
            encodedInteger = obfuscator.obfuscate(encodedInteger);
        }

        return this.codec.encodeIntegerAsString(encodedInteger);
    }

    public int deobfuscate(final String string) {
        int decodedInteger = this.codec.decodeStringAsInteger(string);

        for (int i = this.obfuscators.length - 1; i >= 0; i--) {
            decodedInteger = this.obfuscators[i].deobfuscate(decodedInteger);
        }

        return decodedInteger;
    }
}
