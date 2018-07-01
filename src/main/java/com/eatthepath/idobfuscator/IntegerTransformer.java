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
     * <p>Transforms the given integer into another integer.</p>
     *
     * @param i the integer to transform
     *
     * @return the transformed integer
     */
    int transformInteger(int i);

    /**
     * Reverses the transformation applied by the {@link #transformInteger(int)} method.
     *
     * @param i the transformed integer for which to reverse transformation
     *
     * @return the original integer
     */
    int reverseTransformInteger(int i);
}
