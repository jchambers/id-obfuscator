package com.eatthepath.idobfuscator.benchmark;

import com.eatthepath.idobfuscator.AlphabetCodec;
import com.eatthepath.idobfuscator.util.AlphabetBuilder;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Random;

@State(Scope.Thread)
public class AlphabetCodecBenchmark {

    private AlphabetCodec alphabetCodec;

    private final Random random = new Random();

    private String[] encodedIntegers;

    private static final int ENCODED_INTEGER_COUNT = 100_000;

    @Setup
    public void setUp() {
        this.alphabetCodec = new AlphabetCodec(new AlphabetBuilder().includeLowercaseLatinLetters().build());

        this.encodedIntegers = new String[ENCODED_INTEGER_COUNT];

        for (int i = 0; i < ENCODED_INTEGER_COUNT; i++) {
            this.encodedIntegers[i] = alphabetCodec.encodeIntegerAsString(this.random.nextLong());
        }
    }

    @Benchmark
    public String benchmarkEncodeIntegerAsString() {
        return this.alphabetCodec.encodeIntegerAsString(this.random.nextLong());
    }

    @Benchmark
    public long benchmarkDecodeStringAsInteger() {
        return this.alphabetCodec.decodeStringAsInteger(this.encodedIntegers[this.random.nextInt(this.encodedIntegers.length)]);
    }
}
