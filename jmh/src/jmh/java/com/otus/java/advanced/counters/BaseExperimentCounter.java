package com.otus.java.advanced.counters;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
//@BenchmarkMode(Mode.Throughput)
@State(Scope.Benchmark)
@Fork(value = 1)
//for experiments
@Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
//for smoke test
//@Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
//@Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
public abstract class BaseExperimentCounter {

    protected Counter counter;

    public abstract Counter buildCounter();

    @Setup(Level.Trial)
    public void setUp() {
        counter = buildCounter();
    }


    @State(Scope.Benchmark)
    public abstract static class SimpleBenchmarks extends BaseExperimentCounter {

        @Benchmark
        @Threads(Threads.MAX)
        public void multithreadRead(Blackhole blackhole) {
            blackhole.consume(counter.read());
        }

        @Benchmark
        @Threads(Threads.MAX)
        public void multithreadWrite(Blackhole blackhole) {
            blackhole.consume(counter.write());
        }

        @Benchmark
        public void singleRead(Blackhole blackhole) {
            blackhole.consume(counter.read());
        }

        @Benchmark
        public void singleWrite(Blackhole blackhole) {
            blackhole.consume(counter.write());
        }
    }


    @State(Scope.Group)
    public abstract static class GroupedBenchmarks extends BaseExperimentCounter {

        //region 1 write - N read
        @Benchmark
        @Group("g1_write_1_read_1")
        @GroupThreads(1)
        public void w1_r1_Read(Blackhole blackhole) {
            blackhole.consume(counter.read());
        }

        @Benchmark
        @Group("g1_write_1_read_1")
        @GroupThreads(1)
        public void w1_r1_Write(Blackhole blackhole) {
            blackhole.consume(counter.write());
        }


        @Benchmark
        @Group("g2_write_1_read_10")
        @GroupThreads(10)
        public void w1_r10_Read(Blackhole blackhole) {
            blackhole.consume(counter.read());
        }

        @Benchmark
        @Group("g2_write_1_read_10")
        @GroupThreads(1)
        public void w1_r10_Write(Blackhole blackhole) {
            blackhole.consume(counter.write());
        }


        @Benchmark
        @Group("g3_write_1_read_100")
        @GroupThreads(100)
        public void w1_r100_Read(Blackhole blackhole) {
            blackhole.consume(counter.read());
        }

        @Benchmark
        @Group("g3_write_1_read_100")
        @GroupThreads(1)
        public void w1_r100_Write(Blackhole blackhole) {
            blackhole.consume(counter.write());
        }
        //endregion


        //region 10 write - N read
        @Benchmark
        @Group("g4_write_10_read_1")
        @GroupThreads(1)
        public void w10_r1_Read(Blackhole blackhole) {
            blackhole.consume(counter.read());
        }

        @Benchmark
        @Group("g4_write_10_read_1")
        @GroupThreads(10)
        public void w10_r1_Write(Blackhole blackhole) {
            blackhole.consume(counter.write());
        }


        @Benchmark
        @Group("g5_write_10_read_10")
        @GroupThreads(10)
        public void w10_r10_Read(Blackhole blackhole) {
            blackhole.consume(counter.read());
        }

        @Benchmark
        @Group("g5_write_10_read_10")
        @GroupThreads(10)
        public void w10_r10_Write(Blackhole blackhole) {
            blackhole.consume(counter.write());
        }


        @Benchmark
        @Group("g6_write_10_read_100")
        @GroupThreads(100)
        public void w10_r100_Read(Blackhole blackhole) {
            blackhole.consume(counter.read());
        }

        @Benchmark
        @Group("g6_write_10_read_100")
        @GroupThreads(10)
        public void w10_r100_Write(Blackhole blackhole) {
            blackhole.consume(counter.write());
        }
        //endregion


        //region 100 write - N read
        @Benchmark
        @Group("g7_write_100_read_1")
        @GroupThreads(1)
        public void w100_r1_Read(Blackhole blackhole) {
            blackhole.consume(counter.read());
        }

        @Benchmark
        @Group("g7_write_100_read_1")
        @GroupThreads(100)
        public void w100_r1_Write(Blackhole blackhole) {
            blackhole.consume(counter.write());
        }


        @Benchmark
        @Group("g8_write_100_read_10")
        @GroupThreads(10)
        public void w100_r10_Read(Blackhole blackhole) {
            blackhole.consume(counter.read());
        }

        @Benchmark
        @Group("g8_write_100_read_10")
        @GroupThreads(100)
        public void w100_r10_Write(Blackhole blackhole) {
            blackhole.consume(counter.write());
        }


        @Benchmark
        @Group("g9_write_100_read_100")
        @GroupThreads(100)
        public void w100_r100_Read(Blackhole blackhole) {
            blackhole.consume(counter.read());
        }

        @Benchmark
        @Group("g9_write_100_read_100")
        @GroupThreads(100)
        public void w100_r100_Write(Blackhole blackhole) {
            blackhole.consume(counter.write());
        }
        //endregion
    }
}
