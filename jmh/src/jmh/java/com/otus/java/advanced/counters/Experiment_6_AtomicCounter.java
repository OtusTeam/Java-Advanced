package com.otus.java.advanced.counters;

public class Experiment_6_AtomicCounter {

    public static class Experiment_6_AtomicCounterSimple extends BaseExperimentCounter.SimpleBenchmarks {

        @Override
        public Counter buildCounter() {
            return new CounterImplementations.AtomicCounter();
        }
    }

    public static class Experiment_6_AtomicCounterGrouped extends BaseExperimentCounter.GroupedBenchmarks {

        @Override
        public Counter buildCounter() {
            return new CounterImplementations.AtomicCounter();
        }
    }
}
