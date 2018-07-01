package com.eatthepath.idobfuscator;

import com.eatthepath.idobfuscator.util.AlphabetBuilder;

public class ExampleApp {

    public static void main(final String[] args) {
        final AlphabetCodec codec = new AlphabetCodec(new AlphabetBuilder()
                .includeUppercaseLatinLetters()
                .includeDigits()
                .excludeVowels()
                .excludeVisuallySimilarCharacters()
                .shuffleWithRandomSeed(0x4800211a78094023L)
                .build());

        final MultiplicativeInverseIntegerTransformer inverse =
                new MultiplicativeInverseIntegerTransformer(0x5ee544ad);

        final BitRotationIntegerTransformer rotate = new BitRotationIntegerTransformer(22);
        final OffsetIntegerTransformer offset = new OffsetIntegerTransformer(0x3b2f0474);
        final XorIntegerTransformer xor = new XorIntegerTransformer(0xd0593242);

        final IntegerObfuscationPipeline pipeline =
                new IntegerObfuscationPipeline(codec, inverse, rotate, offset, xor);

        System.out.println("| ID | Obfuscated ID |");
        System.out.println("|----|---------------|");

        for (int id = 0; id < 10; id++) {
            System.out.format("| %d  | %-13s |\n", id, pipeline.obfuscate(id));
            assert id == pipeline.deobfuscate(pipeline.obfuscate(id));
        }
    }
}
