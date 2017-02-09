package com.eatthepath.idobfuscator;

/**
 * Reversibly transforms a long integer it to a (presumably) different long integer. Transformers are generally arranged
 * into an {@link LongObfuscationPipeline}, which also couples the transformers with a {@link LongCodec}.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 *
 * @see LongObfuscationPipeline
 */
public interface LongTransformer {

    /**
     * Reversibly transforms the given integer it into another integer. Transformations must be reversible, and it must
     * be true for all values of {@code i} that {@code i == reverseTransformInteger(transformInteger(i))}.
     *
     * @param l the integer to transform
     *
     * @return the transformed integer
     */
    long transformLong(long l);

    /**
     * Reverses the transformation applied by the {@link IntegerTransformer#transformInteger(int)} method.
     * Transformations must be reversible, and it must be true for all values of {@code i} that
     * {@code i == reverseTransformInteger(transformInteger(i))}.
     *
     * @param l the transformed integer for which to reverse transformation
     *
     * @return the original integer
     */
    long reverseTransformLong(long l);
}
