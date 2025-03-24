package com.otus.java.advanced.counters;

public class Experiment_3_SynchronizedCounter {

    public static class Experiment_3_SynchronizedCounterSimple extends BaseExperimentCounter.SimpleBenchmarks {

        @Override
        public Counter buildCounter() {
            return new CounterImplementations.SynchronizedCounter();
        }
    }

    public static class Experiment_3_SynchronizedCounterGrouped extends BaseExperimentCounter.GroupedBenchmarks {

        @Override
        public Counter buildCounter() {
            return new CounterImplementations.SynchronizedCounter();
        }
    }
}
