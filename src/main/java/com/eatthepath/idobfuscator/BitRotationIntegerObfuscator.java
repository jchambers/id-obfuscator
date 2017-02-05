package com.eatthepath.idobfuscator;

public class BitRotationIntegerObfuscator implements IntegerObfuscator {

    private final int distance;

    public BitRotationIntegerObfuscator(final int distance) {
        if (distance <= 0 || distance >= Integer.SIZE) {
            throw new IllegalArgumentException(String.format("Rotation distance must be between 0 and %d bits", Integer.SIZE));
        }

        this.distance = distance;
    }

    @Override
    public int obfuscate(final int i) {
        return (i << this.distance) | (i >>> (Integer.SIZE - this.distance));
    }

    @Override
    public int deobfuscate(final int i) {
        return (i >>> this.distance) | (i << (Integer.SIZE - this.distance));
    }
}
