package com.eatthepath.idobfuscator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class IntegerObfuscationPipelineTest {
    
    private final AlphabetCodec codec = new AlphabetCodec('1', '2', '3', '4', '5', '6', 'a', 'b', 'c', 'd', 'e', 'f');
    private final XorIntegerTransformer xorObfuscator = new XorIntegerTransformer(3455555);
    private final MultiplicativeInverseIntegerTransformer multiplicativeInverseObfuscator =
            new MultiplicativeInverseIntegerTransformer(873795);

    @Test(expected = NullPointerException.class)
    public void testIntegerObfuscationPipelineNullCodec() {
        new IntegerObfuscationPipeline(null);
    }

    @Test
    @Parameters({ "0", "1", "-1", "2147483647", "-2147483648"})
    public void testObfuscateDeobfuscate(final int l) {
        final IntegerObfuscationPipeline pipeline =
                new IntegerObfuscationPipeline(this.codec, this.xorObfuscator, this.multiplicativeInverseObfuscator);

        assertEquals(l, pipeline.deobfuscate(pipeline.obfuscate(l)));
    }

    @Test
    @Parameters({ "0", "1", "-1", "2147483647", "-2147483648"})
    public void testObfuscateDeobfuscateCodecOnly(final int l) {
        final IntegerObfuscationPipeline pipeline = new IntegerObfuscationPipeline(this.codec);

        assertEquals(l, pipeline.deobfuscate(pipeline.obfuscate(l)));
    }
}
