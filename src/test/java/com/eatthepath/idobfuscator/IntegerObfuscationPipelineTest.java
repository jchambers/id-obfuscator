package com.eatthepath.idobfuscator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.eatthepath.idobfuscator.util.BitwiseOperationUtil;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class IntegerObfuscationPipelineTest {

    private final AlphabetCodec codec = new AlphabetCodec('1', '2', '3', '4', '5', '6', 'a', 'b', 'c', 'd', 'e', 'f');
    private final XorIntegerTransformer xorObfuscator = new XorIntegerTransformer(3455555);
    private final MultiplicativeInverseIntegerTransformer multiplicativeInverseObfuscator =
            new MultiplicativeInverseIntegerTransformer(873795);

    @Test(expected = NullPointerException.class)
    public void testIntegerObfuscationPipelineNullCodec() {
        new IntegerObfuscationPipeline(Integer.SIZE, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntegerObfuscationPipelineTooFewBits() {
        new IntegerObfuscationPipeline(0, this.codec);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntegerObfuscationPipelineTooManyBits() {
        new IntegerObfuscationPipeline(Long.SIZE + 1, this.codec);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testObfuscateIntegerTooWide() {
        new IntegerObfuscationPipeline(Integer.SIZE, this.codec).obfuscate(Integer.MAX_VALUE + 1L);
    }

    @Test
    @Parameters(method = "getParameters")
    public void testObfuscateDeobfuscate(final long l, final int nBits) {
        final IntegerObfuscationPipeline pipeline = new IntegerObfuscationPipeline(nBits, this.codec,
                this.xorObfuscator, this.multiplicativeInverseObfuscator);

        assertEquals(l, pipeline.deobfuscate(pipeline.obfuscate(l)));
    }

    @Test
    @Parameters(method = "getParameters")
    public void testObfuscateDeobfuscateCodecOnly(final long l, final int nBits) {
        final IntegerObfuscationPipeline pipeline = new IntegerObfuscationPipeline(nBits, this.codec);

        assertEquals(l, pipeline.deobfuscate(pipeline.obfuscate(l)));
    }

    @SuppressWarnings("unused")
    private List<List<?>> getParameters() {
        final List<List<?>> parameters = new ArrayList<>();

        for (final int nBits : new int[] { 17, 32, 44, 64 }) {
            parameters.add(Arrays.asList(0, nBits));
            parameters.add(Arrays.asList(1, nBits));
            parameters.add(Arrays.asList(-1, nBits));
            parameters.add(Arrays.asList(BitwiseOperationUtil.getMinValueForSize(nBits), nBits));
            parameters.add(Arrays.asList(BitwiseOperationUtil.getMaxValueForSize(nBits), nBits));
        }

        return parameters;
    }
}
