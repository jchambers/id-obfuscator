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
public abstract class LongTransformerTest {

    protected abstract LongTransformer[] getTransformers();

    @Test
    @Parameters(method = "getParametersForTransformLong")
    public void testTransformReverseTransform(final LongTransformer transformer, final long l) {
        assertEquals(l, transformer.reverseTransformLong(transformer.transformLong(l)));
    }

    @SuppressWarnings("unused")
    private List<List<?>> getParametersForTransformLong() {
        final List<List<?>> parameters = new ArrayList<>();

        for (final LongTransformer transformer : this.getTransformers()) {
            parameters.add(Arrays.asList(transformer, 0));
            parameters.add(Arrays.asList(transformer, 1));
            parameters.add(Arrays.asList(transformer, -1));
            parameters.add(Arrays.asList(transformer, Long.MAX_VALUE));
            parameters.add(Arrays.asList(transformer, Long.MIN_VALUE));
        }

        return parameters;
    }
}
