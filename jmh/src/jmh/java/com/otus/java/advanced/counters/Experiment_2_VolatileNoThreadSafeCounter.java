package com.otus.java.advanced.counters;

public class Experiment_2_VolatileNoThreadSafeCounter {

    public static class Experiment_2_VolatileNoThreadSafeCounterSimple extends BaseExperimentCounter.SimpleBenchmarks {

        @Override
        public Counter buildCounter() {
            return new CounterImplementations.VolatileNoThreadSafeCounter();
        }
    }

    public static class Experiment_2_VolatileNoThreadSafeCounterGrouped extends BaseExperimentCounter.GroupedBenchmarks {

        @Override
        public Counter buildCounter() {
            return new CounterImplementations.VolatileNoThreadSafeCounter();
        }
    }
}
