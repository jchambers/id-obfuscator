package com.eatthepath.idobfuscator;

/**
 * Reversibly transforms long integers into strings and vice versa. This is usually the last (and sometimes only) step
 * of an obfuscation process.
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
     * @param l the long integer to represent as a string
     *
     * @return a string representation of the given long integer
     */
    String encodeLongAsString(long l);

    /**
     * Decodes the given string as a long integer. Decoding must reverse the encoding process, and it must be true for
     * all values of {@code l} that {@code l == decodeStringAsLong(encodeLongAsString(l))}.
     *
     * @param string the string to decode as a long integer
     *
     * @return the long integer represented by the given encoded string
     */
    long decodeStringAsLong(String string);
}
