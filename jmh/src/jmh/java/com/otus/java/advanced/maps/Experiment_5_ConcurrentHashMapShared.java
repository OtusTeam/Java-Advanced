package com.otus.java.advanced.maps;

import com.otus.java.advanced.SharedData;

public class Experiment_5_ConcurrentHashMapShared {

    public static class Experiment_5_ConcurrentHashMapSharedSimple extends BaseExperimentSharedData.SimpleBenchmarks {

        @Override
        public SharedData buildSharedData() {
            return new SharedDataImplementations.ConcurrentHashMapShared();
        }
    }

    public static class Experiment_5_ConcurrentHashMapSharedGrouped extends BaseExperimentSharedData.GroupedBenchmarks {

        @Override
        public SharedData buildSharedData() {
            return new SharedDataImplementations.ConcurrentHashMapShared();
        }
    }
}
