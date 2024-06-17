package com.otus.java.advanced.synchronizers.benchmarktesting;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class CountDownLatchBenchmark {
    private CountDownLatch countDownLatch;

    @Setup(Level.Iteration)
    public void setUp() {
        countDownLatch = new CountDownLatch(1); // инициализация CountDownLatch с одним счетчиком
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testCountDownLatch() {
        countDownLatch.countDown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CountDownLatchBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(3)
                .build();

        new Runner(opt).run();
    }
}
