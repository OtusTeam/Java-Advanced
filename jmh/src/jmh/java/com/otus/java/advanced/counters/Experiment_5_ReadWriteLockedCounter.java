package com.otus.java.advanced.counters;

public class Experiment_5_ReadWriteLockedCounter {

    public static class Experiment_5_ReadWriteLockedCounterSimple extends BaseExperimentCounter.SimpleBenchmarks {

        @Override
        public Counter buildCounter() {
            return new CounterImplementations.ReadWriteLockedCounter();
        }
    }

    public static class Experiment_5_ReadWriteLockedCounterGrouped extends BaseExperimentCounter.GroupedBenchmarks {

        @Override
        public Counter buildCounter() {
            return new CounterImplementations.ReadWriteLockedCounter();
        }
    }
}

