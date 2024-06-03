package com.otus.java.advanced;

import com.otus.java.Fibonacci;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

public class FibonacciBenchmarks {

    //region lesson Default parameters DEMO. It is too long...
    @Benchmark
    public void fibClassicDefault(Blackhole bh) {
        bh.consume(Fibonacci.fibClassic(3));
    }
    //endregion


    //region lesson Short test DEMO
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 1, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1, time = 5, timeUnit = TimeUnit.SECONDS)
    public void fibClassicShort(Blackhole bh) {
        bh.consume(Fibonacci.fibClassic(3));
    }
    //endregion


    //region lesson No fork DEMO
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 0)
    @Warmup(iterations = 1, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 1, time = 5, timeUnit = TimeUnit.SECONDS)
    public void fibClassicNoFork(Blackhole bh) {
        bh.consume(Fibonacci.fibClassic(3));
    }
    //endregion


    //region lesson State DEMO

    @State(Scope.Benchmark)
    public static class ExecutionPlan {

        @Param({"1", "10", "20", "30"})
        public int n;

        @Setup(Level.Trial)
        public void setUpTrial() {
            System.out.println("TRIAL: Before " + n);
        }

        @TearDown(Level.Trial)
        public void tearDownTrial() {
            System.out.println("TRIAL: After " + n);
        }

        @Setup(Level.Iteration)
        public void setUpIteration() {
            System.out.println("ITERATION: Before " + n);
        }

        @TearDown(Level.Iteration)
        public void tearDownIteration() {
            System.out.println("ITERATION: After " + n);
        }

        @Setup(Level.Invocation)
        public void setUpInvocation() {
//            System.out.println("INVOCATION: Before " + n);
        }

        @TearDown(Level.Invocation)
        public void tearDownInvocation() {
//            System.out.println("INVOCATION: After " + n);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 2, warmups = 0)
    @Warmup(iterations = 0)
    @Measurement(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
    public void fibClassicTracing(ExecutionPlan plan, Blackhole bh) {
        bh.consume(Fibonacci.fibClassic(plan.n));
    }

    //endregion


    //region lesson Simple recursive VS tail recursive DEMO
    @Benchmark
    @Fork(value = 1, warmups = 0)
    @BenchmarkMode(Mode.Throughput)
    public void fibClassicRecursiveSimple(Blackhole bh) {
        bh.consume(Fibonacci.fibClassic(30));
    }

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @BenchmarkMode(Mode.Throughput)
    public void fibClassicRecursiveTail(Blackhole bh) {
        bh.consume(Fibonacci.tailRecFib(30));
    }
    //endregion


    //region lesson Multithreading speedUp DEMO
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 1, time = 10, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 2, time = 10, timeUnit = TimeUnit.SECONDS)
    public void fibClassicSpeedUpSingleThread(Blackhole bh) {
        bh.consume(Fibonacci.fibClassic(30));
    }

    @Threads(Threads.MAX)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 1, time = 10, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 2, time = 10, timeUnit = TimeUnit.SECONDS)
    public void fibClassicSpeedUpMaxThreads(Blackhole bh) {
        bh.consume(Fibonacci.fibClassic(30));
    }
    //endregion


    //region lesson Why the field Cnt is the way it is?
    @Benchmark
    @Threads(Threads.MAX)
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 3, warmups = 2)
    @Warmup(iterations = 4, time = 300, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
    public void fibClassicCnt(Blackhole bh) {
        bh.consume(Fibonacci.fibClassic(30));
    }
    //endregion


    //region lesson Mode.All DEMO
    @Benchmark
    @Threads(Threads.MAX)
    @BenchmarkMode(Mode.All)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 30, time = 10, timeUnit = TimeUnit.MILLISECONDS)
    public void fibClassicModeAll(Blackhole bh) {
        bh.consume(Fibonacci.fibClassic(30));
    }
    //endregion
}
