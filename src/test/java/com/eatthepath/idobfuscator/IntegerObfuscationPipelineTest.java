package com.eatthepath.idobfuscator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    @Parameters(method = "getParameters")
    public void testObfuscateDeobfuscate(final long l) {
        final IntegerObfuscationPipeline pipeline =
                new IntegerObfuscationPipeline(this.codec, this.xorObfuscator, this.multiplicativeInverseObfuscator);

        assertEquals(l, pipeline.deobfuscate(pipeline.obfuscate(l)));
    }

    @Test
    @Parameters(method = "getParameters")
    public void testObfuscateDeobfuscateCodecOnly(final long l) {
        final IntegerObfuscationPipeline pipeline = new IntegerObfuscationPipeline(this.codec);

        assertEquals(l, pipeline.deobfuscate(pipeline.obfuscate(l)));
    }

    @SuppressWarnings("unused")
    private List<List<?>> getParameters() {
        final List<List<?>> parameters = new ArrayList<>();

        parameters.add(Collections.singletonList(0));
        parameters.add(Collections.singletonList(1));
        parameters.add(Collections.singletonList(-1));
        parameters.add(Collections.singletonList(Long.MAX_VALUE));
        parameters.add(Collections.singletonList(Long.MIN_VALUE));

        return parameters;
    }
}
