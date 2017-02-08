package com.eatthepath.idobfuscator;

import java.util.Objects;

/**
 * <p>An integer obfuscation pipeline combines any number of {@link IntegerTransformer} instances and exactly one
 * {@link IntegerCodec} to obfuscate and deobfuscate integers.</p>
 *
 * <p>The output of an obfuscation pipeline is determined by the kind, number, order, and configuration of its
 * transformers and by the kind and configuration of its codec. Changing the order of transformers, for example may
 * dramatically alter the output of the pipeline.</p>
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class IntegerObfuscationPipeline {
    private final IntegerTransformer[] transformers;
    private final IntegerCodec codec;

    /**
     * Constructs a new obfuscation pipeline with the given codec and transformers.
     *
     * @param codec the codec to be used to convert potentially-obfuscated integers to and from strings; must not be
     * {@code null}
     * @param transformers any number of transformers to be applied to integers to be obfuscated or deobfuscated; may be
     * empty or {@code null}
     */
    public IntegerObfuscationPipeline(final IntegerCodec codec, final IntegerTransformer... transformers) {
        Objects.requireNonNull(codec, "Codec must not be null");

        this.transformers = transformers;
        this.codec = codec;
    }

    /**
     * Reversibly obfuscates an integer. The pipeline obfuscates the given integer by applying each of this pipeline's
     * transformers in sequence, and then converting the integer to a string with this pipeline's integer-to-string
     * codec.
     *
     * @param i the integer to obfuscate
     *
     * @return a string representation of the obfuscated integer
     */
    public String obfuscate(final int i) {
        int encodedInteger = i;

        for (final IntegerTransformer obfuscator : this.transformers) {
            encodedInteger = obfuscator.transformInteger(encodedInteger);
        }

        return this.codec.encodeIntegerAsString(encodedInteger);
    }

    /**
     * Deobfuscates an integer represented by the given string. The pipeline deobfuscates the integer by first decoding
     * the string with this pipeline's codec, then applying each of the pipeline's transformers in reverse order,
     *
     * @param string the string to decode and deobfuscate
     *
     * @return the deobfuscated integer represented by the given string
     */
    public int deobfuscate(final String string) {
        int decodedInteger = this.codec.decodeStringAsInteger(string);

        for (int i = this.transformers.length - 1; i >= 0; i--) {
            decodedInteger = this.transformers[i].reverseTransformInteger(decodedInteger);
        }

        return decodedInteger;
    }
}
