package com.eatthepath.idobfuscator;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntegerObfuscationPipelineTest {

    private final AlphabetCodec codec = new AlphabetCodec('1', '2', '3', '4', '5', '6', 'a', 'b', 'c', 'd', 'e', 'f');
    private final XorIntegerObfuscator xorObfuscator = new XorIntegerObfuscator(3455555);
    private final MultiplicativeInverseIntegerObfuscator multiplicativeInverseObfuscator =
            new MultiplicativeInverseIntegerObfuscator(873795);

    @Test
    public void testObfuscateDeobfuscate() {
        final IntegerObfuscationPipeline pipeline = new IntegerObfuscationPipeline(this.codec, this.xorObfuscator, this.multiplicativeInverseObfuscator);

        for (final int id : new int[] {0, 1, 77, -77, Integer.MAX_VALUE, Integer.MIN_VALUE }) {
            assertEquals(id, pipeline.deobfuscate(pipeline.obuscate(id)));
        }
    }
}
