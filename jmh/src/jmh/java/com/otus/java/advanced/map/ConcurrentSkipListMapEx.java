package com.otus.java.advanced.map;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Threads(Threads.MAX)
@Fork(value = 1, warmups = 0)
@Warmup(iterations = 1, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@OperationsPerInvocation(10000)
@State(Scope.Benchmark)
public class ConcurrentSkipListMapEx {
    private ConcurrentSkipListMap<String, Integer> concurrentSkipListMap = new ConcurrentSkipListMap<>();

    @Benchmark
    public void benchmarkConcurrentSkipListMapPut() {
        for (int i = 0; i < 10000; i++) {
            concurrentSkipListMap.put(String.valueOf(i), i);
        }
    }

    @Benchmark
    public void benchmarkConcurrentSkipListMapGet(Blackhole blackhole) {
        for (int i = 0; i < 10000; i++) {
            Integer value = concurrentSkipListMap.get(String.valueOf(i));
            blackhole.consume(value);
        }
    }
}
