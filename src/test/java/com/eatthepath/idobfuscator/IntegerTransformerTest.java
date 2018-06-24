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
public abstract class IntegerTransformerTest {

    protected abstract IntegerTransformer[] getTransformers();

    @Test
    @Parameters(method = "getParametersForTransformInteger")
    public void testTransformReverseTransform(final IntegerTransformer transformer, final long l) {
        assertEquals(l, transformer.reverseTransformInteger(transformer.transformInteger(l)));
    }

    @SuppressWarnings("unused")
    private List<List<?>> getParametersForTransformInteger() {
        final List<List<?>> parameters = new ArrayList<>();

        for (final IntegerTransformer transformer : this.getTransformers()) {
            parameters.add(Arrays.asList(transformer, 0));
            parameters.add(Arrays.asList(transformer, 1));
            parameters.add(Arrays.asList(transformer, -1));
            parameters.add(Arrays.asList(transformer, Long.MAX_VALUE));
            parameters.add(Arrays.asList(transformer, Long.MIN_VALUE));
        }

        return parameters;
    }
}
