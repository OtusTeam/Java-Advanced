package com.otus.java.advanced.synchronizers.benchmarktesting;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class PhaserBenchmark {

    private Phaser phaser = new Phaser();

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int testPhaserArrival() {
        phaser.register(); // Регистрация участника
        phaser.arriveAndDeregister(); // Участник прибыл и отмечен как завершивший этап
        return phaser.getPhase(); // Возвращает номер текущего этапа
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(PhaserBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .build();

        new Runner(opt).run();
    }
}