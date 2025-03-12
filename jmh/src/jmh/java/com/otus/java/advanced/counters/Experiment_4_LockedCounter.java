package com.otus.java.advanced.counters;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class Experiment_4_LockedCounter {

    @State(Scope.Benchmark)
    public static class Experiment_4_LockedCounterSimple extends BaseExperimentCounter.SimpleBenchmarks {

        @Override
        public Counter buildCounter() {
            return new CounterImplementations.LockedCounter();
        }
    }

    public static class Experiment_4_LockedCounterGrouped extends BaseExperimentCounter.GroupedBenchmarks {

        @Override
        public Counter buildCounter() {
            return new CounterImplementations.LockedCounter();
        }
    }
}

