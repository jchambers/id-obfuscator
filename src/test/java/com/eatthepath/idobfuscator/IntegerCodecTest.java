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
public abstract class IntegerCodecTest {

    protected abstract IntegerCodec[] getCodecs();

    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "getParametersForEncodeIntegerTooBigForSize")
    public void testEncodeIntegerAsStringTooWide(final IntegerCodec codec, final long l, final int nBits) {
        codec.encodeIntegerAsString(l, nBits);
    }

    @SuppressWarnings("unused")
    private List<List<?>> getParametersForEncodeIntegerTooBigForSize() {
        final List<List<?>> parameters = new ArrayList<>();

        for (final IntegerCodec codec : this.getCodecs()) {
            parameters.add(Arrays.asList(codec, Byte.MIN_VALUE - 1L, Byte.SIZE));
            parameters.add(Arrays.asList(codec, Byte.MAX_VALUE + 1L, Byte.SIZE));
            parameters.add(Arrays.asList(codec, Short.MIN_VALUE - 1L, Short.SIZE));
            parameters.add(Arrays.asList(codec, Short.MAX_VALUE + 1L, Short.SIZE));
            parameters.add(Arrays.asList(codec, Integer.MIN_VALUE - 1L, Integer.SIZE));
            parameters.add(Arrays.asList(codec, Integer.MAX_VALUE + 1L, Integer.SIZE));
        }

        return parameters;
    }

    @Test
    @Parameters(method = "getParametersForEncodeDecode")
    public void testEncodeDecode(final IntegerCodec codec, final long l, final int nBits) {
        assertEquals(l, codec.decodeStringAsInteger(codec.encodeIntegerAsString(l, nBits), nBits));
    }

    @SuppressWarnings("unused")
    private List<List<?>> getParametersForEncodeDecode() {
        final List<List<?>> parameters = new ArrayList<>();

        for (final IntegerCodec codec : this.getCodecs()) {
            for (final int nBits : new int[] { 17, 32, 44, 64 }) {
                parameters.add(Arrays.asList(codec, 0, nBits));
                parameters.add(Arrays.asList(codec, 1, nBits));
                parameters.add(Arrays.asList(codec, -1, nBits));
                parameters.add(Arrays.asList(codec, BitwiseOperationUtil.getMinValueForSize(nBits), nBits));
                parameters.add(Arrays.asList(codec, BitwiseOperationUtil.getMaxValueForSize(nBits), nBits));
            }
        }

        return parameters;
    }
}
