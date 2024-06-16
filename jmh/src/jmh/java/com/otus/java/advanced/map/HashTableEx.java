package com.otus.java.advanced.map;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
//@Threads(Threads.MAX)
@Fork(value = 1, warmups = 0)
@Warmup(iterations = 1, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@OperationsPerInvocation(10000)
@State(Scope.Benchmark)
public class HashTableEx {
    private Hashtable<String, Integer> hashtable = new Hashtable<>();
    @Benchmark
    public void benchmarkHashtablePut() {
        for (int i = 0; i < 10000; i++) {
            hashtable.put(String.valueOf(i), i);
        }
    }

    @Benchmark
    public void benchmarkHashtableGet(Blackhole blackhole) {
        for (int i = 0; i < 10000; i++) {
            Integer value = hashtable.get(String.valueOf(i));
            blackhole.consume(value);
        }
    }
}
