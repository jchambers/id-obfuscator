package com.eatthepath.idobfuscator;

/**
 * Reversibly transforms an integer it to a (presumably) different integer. Transformers are generally arranged into an
 * {@link IntegerObfuscationPipeline}, which also couples the transformers with a {@link IntegerCodec}.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 *
 * @see IntegerObfuscationPipeline
 */
public interface IntegerTransformer {

    /**
     * Reversibly transforms the given integer it into another integer. Transformations must be reversible, and it must
     * be true for all values of {@code i} that {@code i == reverseTransformInteger(transformInteger(i))}.
     *
     * @param i the integer to transform
     *
     * @return the transformed integer
     */
    int transformInteger(int i);

    /**
     * Reverses the transformation applied by the {@link IntegerTransformer#transformInteger(int)} method.
     * Transformations must be reversible, and it must be true for all values of {@code i} that
     * {@code i == reverseTransformInteger(transformInteger(i))}.
     *
     * @param i the transformed integer for which to reverse transformation
     *
     * @return the original integer
     */
    int reverseTransformInteger(int i);
}
