package com.eatthepath.idobfuscator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.eatthepath.idobfuscator.util.BitwiseOperationUtil;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public abstract class IntegerTransformerTest {

    protected abstract IntegerTransformer[] getTransformers();

    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "getParametersForTransformIntegerTooBigForSize")
    public void testTransformIntegerTooBigForSize(final IntegerTransformer transformer, final long l, final int nBits) {
        transformer.transformInteger(l, nBits);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "getParametersForTransformIntegerTooBigForSize")
    public void testReverseTransformIntegerTooBigForSize(final IntegerTransformer transformer, final long l, final int nBits) {
        transformer.reverseTransformInteger(l, nBits);
    }

    @SuppressWarnings("unused")
    private List<List<?>> getParametersForTransformIntegerTooBigForSize() {
        final List<List<?>> parameters = new ArrayList<>();

        for (final IntegerTransformer transformer : this.getTransformers()) {
            parameters.add(Arrays.asList(transformer, Byte.MIN_VALUE - 1L, Byte.SIZE));
            parameters.add(Arrays.asList(transformer, Byte.MAX_VALUE + 1L, Byte.SIZE));
            parameters.add(Arrays.asList(transformer, Short.MIN_VALUE - 1L, Short.SIZE));
            parameters.add(Arrays.asList(transformer, Short.MAX_VALUE + 1L, Short.SIZE));
            parameters.add(Arrays.asList(transformer, Integer.MIN_VALUE - 1L, Integer.SIZE));
            parameters.add(Arrays.asList(transformer, Integer.MAX_VALUE + 1L, Integer.SIZE));
        }

        return parameters;
    }

    @Test
    @Parameters(method = "getParametersForTransformInteger")
    public void testTransformInteger(final IntegerTransformer transformer, final long l, final int nBits) {
        final long transformed = transformer.transformInteger(l, nBits);

        BitwiseOperationUtil.assertValueFitsWithinSize(transformed, nBits);
        assertEquals(BitwiseOperationUtil.signExtendLowestBitsToLong(transformed, nBits), transformed);
    }

    @Test
    @Parameters(method = "getParametersForTransformInteger")
    public void testReverseTransformInteger(final IntegerTransformer transformer, final long l, final int nBits) {
        final long reverseTransformed = transformer.reverseTransformInteger(l, nBits);

        BitwiseOperationUtil.assertValueFitsWithinSize(reverseTransformed, nBits);
        assertEquals(BitwiseOperationUtil.signExtendLowestBitsToLong(reverseTransformed, nBits), reverseTransformed);
    }

    @Test
    @Parameters(method = "getParametersForTransformInteger")
    public void testTransformReverseTransform(final IntegerTransformer transformer, final long l, final int nBits) {
        assertEquals(l, transformer.reverseTransformInteger(transformer.transformInteger(l, nBits), nBits));
    }

    @SuppressWarnings("unused")
    private List<List<?>> getParametersForTransformInteger() {
        final List<List<?>> parameters = new ArrayList<>();

        for (final IntegerTransformer transformer : this.getTransformers()) {
            for (final int nBits : new int[] { 17, 32, 44, 64 }) {
                parameters.add(Arrays.asList(transformer, 0, nBits));
                parameters.add(Arrays.asList(transformer, 1, nBits));
                parameters.add(Arrays.asList(transformer, -1, nBits));
                parameters.add(Arrays.asList(transformer, BitwiseOperationUtil.getMinValueForSize(nBits), nBits));
                parameters.add(Arrays.asList(transformer, BitwiseOperationUtil.getMaxValueForSize(nBits), nBits));
            }
        }

        return parameters;
    }
}
