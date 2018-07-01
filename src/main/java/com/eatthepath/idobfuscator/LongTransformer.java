package com.eatthepath.idobfuscator;

/**
 * Reversibly transforms a long integer it to a (presumably) different integer. Transformers are generally arranged into
 * an {@link LongObfuscationPipeline}, which also couples the transformers with a {@link LongCodec}.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 *
 * @see LongObfuscationPipeline
 */
public interface LongTransformer {

    /**
     * <p>Transforms the given long integer into another integer.</p>
     *
     * @param l the long integer to transform
     *
     * @return the transformed long integer
     */
    long transformLong(long l);

    /**
     * Reverses the transformation applied by the {@link #transformLong(long)} method.
     *
     * @param l the transformed long integer for which to reverse transformation
     *
     * @return the original long integer
     */
    long reverseTransformLong(long l);
}
