package com.eatthepath.idobfuscator;

/**
 * Reversibly transforms integers into strings and vice versa. This is usually the last (and sometimes only) step of an
 * obfuscation process.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 *
 * @see IntegerObfuscationPipeline
 */
public interface IntegerCodec {

    /**
     * <p>Encodes the lowest {@code nBits} bits of the given integer as a string. Encoding must be reversible, and it
     * must be true for all values of {@code i} that {@code i == decodeStringAsInteger(encodeIntegerAsString(i))}. If
     * the given integer cannot be expressed with {@code nBits} bits (i.e. it is larger than the maximum value for the
     * given number of bits or is smaller than the minimum value), this method must throw an
     * {@link IllegalArgumentException}.</p>
     *
     * @param i the integer to represent as a string
     * @param nBits the number of low bits in the given integer to encode; if {@code i} cannot be expressed with
     * {@code nBits} bits, this method must throw an {@link IllegalArgumentException}
     *
     * @return a string representation of the given integer
     *
     * @throws IllegalArgumentException if {@code i} cannot be represented with {@code nBits} bits
     */
    String encodeIntegerAsString(long i, int nBits);

    /**
     * <p>Decodes the given string as an integer. Decoding must reverse the encoding process, and it must be true for
     * all values of {@code i} that {@code i == decodeStringAsInteger(encodeIntegerAsString(i))}.</p>
     *
     * <p>Implementations of this method must throw an {@link IllegalArgumentException} if the decoded value cannot be
     * expressed with {@code nBits} bits (i.e. it is larger than the maximum value for the given number of bits or is
     * smaller than the minimum value).</p>
     *
     * @param string the string to decode as an integer
     * @param nBits the number of bits in which the decoded value must fit; if the decoded value cannot be expressed
     * with {@code nBits} bits, this method must throw an {@link IllegalArgumentException}
     *
     * @return the integer represented by the given encoded string
     *
     * @throws IllegalArgumentException if the decoded value cannot be represented with {@code nBits} bits
     */
    long decodeStringAsInteger(String string, int nBits);
}
