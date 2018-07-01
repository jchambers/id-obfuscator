package com.eatthepath.idobfuscator;

/**
 * Reversibly transforms integers into strings and vice versa. This is usually the last (and sometimes only) step of an
 * obfuscation pipeline.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 *
 * @see IntegerObfuscationPipeline
 */
public interface IntegerCodec {

    /**
     * Encodes the given integer as a string. Encoding must be reversible, and it must be true for all values of
     * {@code i} that {@code i == decodeStringAsInteger(encodeIntegerAsString(i))}.
     *
     * @param i the {@code int} to represent as a string
     *
     * @return a string representation of the given {@code int}
     */
    String encodeIntegerAsString(int i);

    /**
     * <p>Decodes the given string as an integer. Decoding must reverse the encoding process, and it must be true
     * for all values of {@code i} that {@code i == decodeStringAsInteger(encodeIntegerAsString(i))}.</p>
     *
     * @param string the string to decode as an {@code int}
     *
     * @return the {@code int} represented by the given encoded string
     */
    int decodeStringAsInteger(String string);
}
