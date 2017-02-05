package com.eatthepath.idobfuscator;

public class MultiplicativeInverseIntegerObfuscator implements IntegerObfuscator {

    final int multiplier;
    final int inverse;

    public MultiplicativeInverseIntegerObfuscator(final int multiplier) {
        if (multiplier <= 0) {
            throw new IllegalArgumentException("Multiplier must be positive");
        }

        this.multiplier = multiplier;
        this.inverse = this.getMultiplicativeInverse(multiplier);
    }

    public int obfuscate(final int i) {
        return i * this.multiplier;
    }

    public int deobfuscate(final int i) {
        return i * this.inverse;
    }

    private int getMultiplicativeInverse(final int multiplier) {
        long s = 0, previousS = 1;
        long t = 1, previousT = 0;
        long r = multiplier, previousR = (1L << Integer.SIZE);

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
}
