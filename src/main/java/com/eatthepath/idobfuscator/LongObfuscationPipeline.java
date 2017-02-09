package com.eatthepath.idobfuscator;

import java.util.Objects;

/**
 * <p>A long integer obfuscation pipeline combines any number of {@link LongTransformer} instances and exactly one
 * {@link LongCodec} to obfuscate and deobfuscate long integers.</p>
 *
 * <p>The output of an obfuscation pipeline is determined by the kind, number, order, and configuration of its
 * transformers and by the kind and configuration of its codec. Changing the order of transformers, for example may
 * dramatically alter the output of the pipeline.</p>
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class LongObfuscationPipeline {
    private final LongTransformer[] transformers;
    private final LongCodec codec;

    /**
     * Constructs a new obfuscation pipeline with the given codec and transformers.
     *
     * @param codec the codec to be used to convert potentially-obfuscated long integers to and from strings; must not
     * be {@code null}
     * @param transformers any number of transformers to be applied to long integers to be obfuscated or deobfuscated;
     * may be empty or {@code null}
     */
    public LongObfuscationPipeline(final LongCodec codec, final LongTransformer... transformers) {
        Objects.requireNonNull(codec, "Codec must not be null");

        this.transformers = transformers;
        this.codec = codec;
    }

    /**
     * Reversibly obfuscates a long integer. The pipeline obfuscates the given long integer by applying each of this
     * pipeline's transformers in sequence, and then converting the long integer to a string with this pipeline's
     * long-to-string codec.
     *
     * @param l the long integer to obfuscate
     *
     * @return a string representation of the obfuscated long integer
     */
    public String obfuscate(final long l) {
        long encodedLong = l;

        for (final LongTransformer obfuscator : this.transformers) {
            encodedLong = obfuscator.transformLong(encodedLong);
        }

        return this.codec.encodeLongAsString(encodedLong);
    }

    /**
     * Deobfuscates a long integer represented by the given string. The pipeline deobfuscates the long integer by first
     * decoding the string with this pipeline's codec, then applying each of the pipeline's transformers in reverse
     * order.
     *
     * @param string the string to decode and deobfuscate
     *
     * @return the deobfuscated long integer represented by the given string
     */
    public long deobfuscate(final String string) {
        long decodedLong = this.codec.decodeStringAsLong(string);

        for (int i = this.transformers.length - 1; i >= 0; i--) {
            decodedLong = this.transformers[i].reverseTransformLong(decodedLong);
        }

        return decodedLong;
    }
}
