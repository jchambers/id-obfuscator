package com.eatthepath.idobfuscator;

/**
 * Obfuscates an integer by reversibly transforming it to a (presumably) different integer. Obfuscators are generally
 * arranged into a {@link IntegerObfuscationPipeline}, which also couples the obfuscators with a {@link IntegerCodec}.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 *
 * @see IntegerObfuscationPipeline
 */
public interface IntegerObfuscator {
    /**
     * Obfuscates this integer by reversibly transforming it into another integer. Obfuscation must be reversible, and
     * it must be true for all values of {@code i} that {@code i == deobfuscate(obfuscate(i))}.
     *
     * @param i the integer to obfuscate
     *
     * @return the obfuscated form of the given integer
     */
    int obfuscate(int i);

    /**
     * Deobfuscates this integer by reversing the transformation applied by the
     * {#link {@link IntegerObfuscator#obfuscate(int)} method. Obfuscation must be reversible, and it must be true for
     * all values of {@code i} that {@code i == deobfuscate(obfuscate(i))}.
     *
     * @param i the obfuscated integer to deobfuscate
     *
     * @return the original integer
     */
    int deobfuscate(int i);
}
