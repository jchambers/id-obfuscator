package com.eatthepath.idobfuscator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class LongObfuscationPipelineTest {

    private final AlphabetCodec codec = new AlphabetCodec('1', '2', '3', '4', '5', '6', 'a', 'b', 'c', 'd', 'e', 'f');
    private final XorLongTransformer xorObfuscator = new XorLongTransformer(3455555);
    private final MultiplicativeInverseLongTransformer multiplicativeInverseObfuscator =
            new MultiplicativeInverseLongTransformer(873795);

    @Test(expected = NullPointerException.class)
    public void testIntegerObfuscationPipelineNullCodec() {
        new LongObfuscationPipeline(null);
    }

    @Test
    @Parameters({ "0", "1", "-1", "9223372036854775807", "-9223372036854775808"})
    public void testObfuscateDeobfuscate(final long l) {
        final LongObfuscationPipeline pipeline =
                new LongObfuscationPipeline(this.codec, this.xorObfuscator, this.multiplicativeInverseObfuscator);

        assertEquals(l, pipeline.deobfuscate(pipeline.obfuscate(l)));
    }

    @Test
    @Parameters({ "0", "1", "-1", "9223372036854775807", "-9223372036854775808"})
    public void testObfuscateDeobfuscateCodecOnly(final long l) {
        final LongObfuscationPipeline pipeline = new LongObfuscationPipeline(this.codec);

        assertEquals(l, pipeline.deobfuscate(pipeline.obfuscate(l)));
    }
}
