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

        final BitRotationIntegerTransformer rotate = new BitRotationIntegerTransformer(17);
        final OffsetIntegerTransformer offset = new OffsetIntegerTransformer(785374208);
        final XorIntegerTransformer xor = new XorIntegerTransformer(4444266);
        final MultiplicativeInverseIntegerTransformer inverse =
                new MultiplicativeInverseIntegerTransformer(5237459);

        final IntegerObfuscationPipeline pipeline = new IntegerObfuscationPipeline(Integer.SIZE, codec,
                rotate, offset, xor, inverse);

        System.out.println("| ID | Obfuscated ID  |");
        System.out.println("|----|----------------|");

        for (int id = 0; id < 10; id++) {
            System.out.format("| %d  | %-14s |\n", id, pipeline.obfuscate(id));
            assert id == pipeline.deobfuscate(pipeline.obfuscate(id));
        }
    }
}
