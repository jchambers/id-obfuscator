package com.eatthepath.idobfuscator;

import com.eatthepath.idobfuscator.util.AlphabetBuilder;

public class ExampleApp {

    public static void main(final String[] args) {
        final AlphabetCodec codec = new AlphabetCodec(new AlphabetBuilder()
                .includeUppercaseLatinLetters()
                .excludeVowels()
                .excludeVisuallySimilarCharacters()
                .shuffleWithRandomSeed(95839275)
                .build());

        final BitRotationIntegerObfuscator rotate = new BitRotationIntegerObfuscator(17);
        final OffsetIntegerObfuscator offset = new OffsetIntegerObfuscator(785374208);
        final XorIntegerObfuscator xor = new XorIntegerObfuscator(4444266);
        final MultiplicativeInverseIntegerObfuscator inverse =
                new MultiplicativeInverseIntegerObfuscator(5237459);

        final IntegerObfuscationPipeline pipeline = new IntegerObfuscationPipeline(codec,
                rotate, offset, xor, inverse);

        for (int id = 0; id < 10; id++) {
            final String obfuscatedId = pipeline.obuscate(id);
            System.out.format("%d -> %s -> %d\n", id, obfuscatedId, pipeline.deobfuscate(obfuscatedId));
        }
    }
}
