package com.otus.java.advanced.synchronizers.benchmarktesting;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class ExchangerBenchmark {

    private Exchanger<Integer> exchanger = new Exchanger<>();

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public Integer testExchanger() throws InterruptedException {
        return exchanger.exchange(42); // Обмен значением с другим потоком
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ExchangerBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .build();

        new Runner(opt).run();
    }
}