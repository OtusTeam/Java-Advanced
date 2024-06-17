package com.otus.java.advanced.synchronizers.benchmarktesting;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class SemaphoreBenchmark {
    private Semaphore semaphore;

    @Setup(Level.Iteration)
    public void setUp() {
        semaphore = new Semaphore(1); // инициализация семафора с одним разрешением
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testSemaphore() throws InterruptedException {
        semaphore.acquire();
        semaphore.release();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SemaphoreBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .build();

        new Runner(opt).run();
    }
}
