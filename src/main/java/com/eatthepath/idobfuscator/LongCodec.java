package com.eatthepath.idobfuscator;

/**
 * Reversibly transforms long integers into strings and vice versa. This is usually the last (and sometimes only) step
 * of an obfuscation pipeline.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 *
 * @see LongObfuscationPipeline
 */
public interface LongCodec {

    /**
     * Encodes the given long integer as a string. Encoding must be reversible, and it must be true for all values of
     * {@code l} that {@code l == decodeStringAsLong(encodeLongAsString(l))}.
     *
     * @param l the {@code long} to represent as a string
     *
     * @return a string representation of the given {@code long}
     */
    String encodeLongAsString(long l);

    /**
     * <p>Decodes the given string as a long integer. Decoding must reverse the encoding process, and it must be true
     * for all values of {@code l} that {@code l == decodeStringAsLong(encodeLongAsString(l))}.</p>
     *
     * @param string the string to decode as a {@code long}
     *
     * @return the {@code long} represented by the given encoded string
     */
    long decodeStringAsLong(String string);
}
