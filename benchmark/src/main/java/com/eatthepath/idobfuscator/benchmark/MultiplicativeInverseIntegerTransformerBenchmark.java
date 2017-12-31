package com.eatthepath.idobfuscator.benchmark;

import com.eatthepath.idobfuscator.MultiplicativeInverseIntegerTransformer;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Random;

@State(Scope.Thread)
public class MultiplicativeInverseIntegerTransformerBenchmark {

    private MultiplicativeInverseIntegerTransformer transformer;

    private final Random random = new Random();

    @Setup
    public void setUp() {
        long multiplier = Math.abs(this.random.nextLong());

        if (multiplier % 2 == 0) {
            multiplier += 1;
        }

        transformer = new MultiplicativeInverseIntegerTransformer(multiplier);
    }

    @Benchmark
    public long benchmarkTransformInteger() {
        return transformer.transformInteger(this.random.nextLong());
    }

    @Benchmark
    public long benchmarkReverseTransformInteger() {
        return transformer.reverseTransformInteger(this.random.nextLong());
    }
}
