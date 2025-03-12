package com.otus.java.advanced.counters;

public class Experiment_1_NoThreadSafeCounter {

    public static class Experiment_1_NoThreadSafeCounterSimple extends BaseExperimentCounter.SimpleBenchmarks {

        @Override
        public Counter buildCounter() {
            return new CounterImplementations.NoThreadSafeCounter();
        }
    }

    public static class Experiment_1_NoThreadSafeCounterGrouped extends BaseExperimentCounter.GroupedBenchmarks {

        @Override
        public Counter buildCounter() {
            return new CounterImplementations.NoThreadSafeCounter();
        }
    }
}