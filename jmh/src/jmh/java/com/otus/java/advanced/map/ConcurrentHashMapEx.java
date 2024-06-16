package com.otus.java.advanced.map;

import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Threads(Threads.MAX)
@Fork(value = 1, warmups = 0)
@Warmup(iterations = 1, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@OperationsPerInvocation(10000)
@State(Scope.Benchmark)
public class ConcurrentHashMapEx {
    private ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();

    @Benchmark
   // @Group("concurrentHashMap")
    public void benchmarkConcurrentHashMapPut() {
        for (int i = 0; i < 10000; i++) {
            concurrentHashMap.put(String.valueOf(i), i);
        }
    }

    @Benchmark
    //@Group("concurrentHashMap")
    public void benchmarkConcurrentHashMapGet(Blackhole blackhole) {
        for (int i = 0; i < 10000; i++) {
            Integer value = concurrentHashMap.get(String.valueOf(i));
            blackhole.consume(value);
        }
    }

}
