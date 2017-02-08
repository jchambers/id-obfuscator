package com.eatthepath.idobfuscator;

/**
 * Transforms integers by performing a bitwise XOR operation with a given "secret" mask.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class XorIntegerTransformer implements IntegerTransformer {

    private final int mask;

    /**
     * Creates a new XOR transformer with the given "secret" mask.
     *
     * @param mask the mask to be applied when transforming numbers
     */
    public XorIntegerTransformer(final int mask) {
        this.mask = mask;
    }

    /**
     * Transforms the given integer by XOR-ing it with this transformer's "secret" mask.
     *
     * @param i the integer to transform
     *
     * @return the transformed integer
     */
    @Override
    public int transformInteger(final int i) {
        return i ^ this.mask;
    }

    /**
     * Reverses an XOR transformation by repeating the original XOR operation.
     *
     * @param i the integer for which to reverse an XOR transformation
     *
     * @return the original integer
     */
    @Override
    public int reverseTransformInteger(final int i) {
        return i ^ this.mask;
    }
}
