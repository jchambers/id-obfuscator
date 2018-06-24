package com.eatthepath.idobfuscator;

import java.math.BigInteger;

/**
 * Transforms integers by multiplying them by a "secret" multiplier and reverses transformations by multiplying by the
 * secret's multiplicative inverse.
 *
 * @author <a href="https://github.com/jchambers">Jon Chambers</a>
 */
public class MultiplicativeInverseIntegerTransformer implements IntegerTransformer {

    private final long multiplier;
    private final transient long inverse;

    /**
     * Constructs a new multiplicative inverse transformer with the given multiplier. Multipliers must be positive and
     * coprime with the size of the number space (which, in this case, just means "must not be divisible by 2"). The
     * multiplicative inverse of the given multiplier is derived automatically.
     *
     * @param multiplier the multiplier to use when transforming integers
     */
    public MultiplicativeInverseIntegerTransformer(final long multiplier) {
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
    public long transformInteger(final long i) {
        return i * this.multiplier;
    }

    /**
     * Reverses a multiplication transformation by multiplying the given integer by the multiplicative inverse of this
     * transformer's "secret" multiplier.
     *
     * @param i the integer for which to reverse a multiplication transformation
     *
     * @return the original integer
     */
    @Override
    public long reverseTransformInteger(final long i) {
        return i * this.inverse;
    }

    private static long getMultiplicativeInverse(final long multiplier) {
        BigInteger s = BigInteger.ZERO, previousS = BigInteger.ONE;
        BigInteger t = BigInteger.ONE, previousT = BigInteger.ZERO;
        BigInteger r = BigInteger.valueOf(multiplier), previousR = BigInteger.ONE.shiftLeft(Long.SIZE);

        while (!BigInteger.ZERO.equals(r)) {
            final BigInteger q = previousR.divide(r);

            {
                final BigInteger tempR = r;
                r = previousR.subtract(q.multiply(r));
                previousR = tempR;
            }

            {
                final BigInteger tempS = s;
                s = previousS.subtract(q.multiply(s));
                previousS = tempS;
            }

            {
                final BigInteger tempT = t;
                t = previousT.subtract(q.multiply(t));
                previousT = tempT;
            }
        }

        return previousT.longValue();
    }

    @Override
    public String toString() {
        return String.format("MultiplicativeInverseIntegerTransformer [multiplier=%d]", this.multiplier);
    }
}
