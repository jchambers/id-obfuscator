package com.eatthepath.idobfuscator;

import com.eatthepath.idobfuscator.util.BitwiseOperationUtil;

/**
 * <p>Reversibly transforms an integer it to a (presumably) different integer. Transformers are generally arranged into
 * an {@link IntegerObfuscationPipeline}, which also couples the transformers with a {@link IntegerCodec}.</p>
 *
 * <p>Transformers operate on the lowest {@code nBits} bits of a given {@code long}. This allows callers to effectively
 * obfuscate integers of any size up to 64 bits.</p>
 *
 * <p>All transformations must obey the following rules:</p>
 *
 * <ul>
 *  <li>{@code reverseTransformInteger(transformInteger(i)) == i}</li>
 *  <li>Transformers must throw an {@link IllegalArgumentException} if the given integer cannot be expressed with
 *  {@code nBits} bits (i.e. it is greater than the maximum value for an {@code nBits}-bit integer or less than the
 *  minimum value)</li>
 *  <li>The result of {@code transformInteger(i)} and {@code reverseTransformInteger(i)} (like {@code i} itself) must
 *  fit within {@code nBits} bits</li>
 *  <li>The {@code long} representation of the result of {@code transformInteger(i)} and
 *  {@code reverseTransformInteger(i)} must be equal to the lowest {@code nBits} bits of the result sign-extended to
 *  64 bits</li>
 * </ul>
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 *
 * @see BitwiseOperationUtil
 * @see IntegerObfuscationPipeline
 */
public interface IntegerTransformer {

    /**
     * <p>Transforms the given integer into another integer.</p>
     *
     * @param i the integer to transform
     * @param nBits the number of low bits in the given integer to which the transformation should be applied; if
     * {@code i} cannot be expressed with {@code nBits} bits, this method must throw an
     * {@link IllegalArgumentException}.
     *
     * @return the transformed integer
     *
     * @throws IllegalArgumentException if {@code i} cannot be represented with {@code nBits} bits
     */
    long transformInteger(long i, int nBits);

    /**
     * Reverses the transformation applied by the {@link IntegerTransformer#transformInteger(long, int)} method.
     *
     * @param i the transformed integer for which to reverse transformation
     * @param nBits the number of low bits in the given integer to which the transformation should be applied; if
     * {@code i} cannot be expressed with {@code nBits} bits, this method must throw an
     * {@link IllegalArgumentException}.
     *
     * @return the original integer
     *
     * @throws IllegalArgumentException if {@code i} cannot be represented with {@code nBits} bits
     */
    long reverseTransformInteger(long i, int nBits);
}
