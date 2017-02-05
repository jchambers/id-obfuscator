package com.eatthepath.idobfuscator;

import java.util.Objects;

/**
 * An integer obfuscation pipeline combines any number of {@link IntegerObfuscator} instances and exactly one
 * {@link IntegerCodec} to obfuscate and deobfuscate integers.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class IntegerObfuscationPipeline {
    private final IntegerObfuscator[] obfuscators;
    private final IntegerCodec codec;

    /**
     * Constructs a new obfuscation pipeline with the given codec and obfuscators.
     *
     * @param codec the codec to be used to convert potentially-obfuscated integers to and from strings; must not be
     * {@code null}
     * @param obfuscators any number of obfuscators to be applied to integers to be obfuscated or deobfuscated; may be
     * empty or {@code null}
     */
    public IntegerObfuscationPipeline(final IntegerCodec codec, final IntegerObfuscator... obfuscators) {
        Objects.requireNonNull(codec, "Codec must not be null");

        this.obfuscators = obfuscators;
        this.codec = codec;
    }

    /**
     * Reversibly obfuscates an integer. The pipeline obfuscates the given integer by applying each of this pipeline's
     * obfuscators in sequence, and then converting the integer to a string with this pipeline's integer-to-string codec.
     *
     * @param i the integer to obfuscate
     *
     * @return a string representation of the obfuscated integer
     */
    public String obuscate(final int i) {
        int encodedInteger = i;

        for (final IntegerObfuscator obfuscator : this.obfuscators) {
            encodedInteger = obfuscator.obfuscate(encodedInteger);
        }

        return this.codec.encodeIntegerAsString(encodedInteger);
    }

    /**
     * Deobfuscates an integer represented by the given string. The pipeline deobfuscates the integer by first decoding
     * the string with this pipeline's codec, then applying each of the pipeline's obfuscators in reverse order,
     *
     * @param string the string to decode and deobfuscate
     *
     * @return the deobfuscated integer represented by the given string
     */
    public int deobfuscate(final String string) {
        int decodedInteger = this.codec.decodeStringAsInteger(string);

        for (int i = this.obfuscators.length - 1; i >= 0; i--) {
            decodedInteger = this.obfuscators[i].deobfuscate(decodedInteger);
        }

        return decodedInteger;
    }
}
