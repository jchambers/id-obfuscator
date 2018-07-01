package com.eatthepath.idobfuscator;

/**
 * Transforms integers by multiplying them by a "secret" multiplier and reverses transformations by multiplying by the
 * secret's multiplicative inverse.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class MultiplicativeInverseIntegerTransformer implements IntegerTransformer {

    private final int multiplier;
    private final transient int inverse;

    /**
     * Constructs a new multiplicative inverse transformer with the given multiplier. Multipliers must be positive and
     * coprime with the size of the number space (which, in this case, just means "must not be divisible by 2"). The
     * multiplicative inverse of the given multiplier is derived automatically.
     *
     * @param multiplier the multiplier to use when transforming integers
     */
    public MultiplicativeInverseIntegerTransformer(final int multiplier) {
        if (multiplier <= 0) {
            throw new IllegalArgumentException("Multiplier must be positive");
        } else if (multiplier % 2 == 0) {
            throw new IllegalArgumentException("Multiplier must not be divisible by 2");
        }

        this.multiplier = multiplier;

        this.inverse = getMultiplicativeInverse(this.multiplier);
    }

    /**
     * Transforms the given integer by multiplying it by this transformer's "secret" multiplier.
     *
     * @param i the integer to transform
     *
     * @return the integer multiplied by this transformer's "secret" multiplier
     */
    @Override
    public int transformInteger(final int i) {
        return i * this.multiplier;
    }

    /**
     * Reverses a multiplication transformation by multiplying the given integer by the multiplicative inverse of
     * this transformer's "secret" multiplier.
     *
     * @param i the integer for which to reverse a multiplication transformation
     *
     * @return the original integer
     */
    @Override
    public int reverseTransformInteger(final int i) {
        return i * this.inverse;
    }

    private static int getMultiplicativeInverse(final int multiplier) {
        long s = 0, previousS = 1;
        long t = 1, previousT = 0;
        long r = multiplier, previousR = 1L << Integer.SIZE;

        while (r != 0) {
            final long q = previousR / r;

            {
                final long tempR = r;
                r = previousR - (q * r);
                previousR = tempR;
            }

            {
                final long tempS = s;
                s = previousS - (q * s);
                previousS = tempS;
            }

            {
                final long tempT = t;
                t = previousT - (q * t);
                previousT = tempT;
            }
        }

        return (int) previousT;
    }

    @Override
    public String toString() {
        return String.format("MultiplicativeInverseIntegerTransformer [multiplier=%d]", this.multiplier);
    }
}
