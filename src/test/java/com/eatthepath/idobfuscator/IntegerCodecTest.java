package com.eatthepath.idobfuscator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public abstract class IntegerCodecTest {

    protected abstract IntegerCodec[] getCodecs();

    @Test
    @Parameters(method = "getParametersForEncodeDecode")
    public void testEncodeDecode(final IntegerCodec codec, final long l) {
        assertEquals(l, codec.decodeStringAsInteger(codec.encodeIntegerAsString(l)));
    }

    @SuppressWarnings("unused")
    private List<List<?>> getParametersForEncodeDecode() {
        final List<List<?>> parameters = new ArrayList<>();

        for (final IntegerCodec codec : this.getCodecs()) {
            parameters.add(Arrays.asList(codec, 0));
            parameters.add(Arrays.asList(codec, 1));
            parameters.add(Arrays.asList(codec, -1));
            parameters.add(Arrays.asList(codec, Long.MAX_VALUE));
            parameters.add(Arrays.asList(codec, Long.MIN_VALUE));
        }

        return parameters;
    }
}
