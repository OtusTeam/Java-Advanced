package com.otus.java.advanced.maps;

import com.otus.java.advanced.SharedData;

public class Experiment_2_SynchronizedHashMapShared {

    public static class Experiment_2_SynchronizedHashMapSharedSimple extends BaseExperimentSharedData.SimpleBenchmarks {

        @Override
        public SharedData buildSharedData() {
            return new SharedDataImplementations.SynchronizedHashMapShared();
        }
    }

    public static class Experiment_2_SynchronizedHashMapSharedGrouped extends BaseExperimentSharedData.GroupedBenchmarks {

        @Override
        public SharedData buildSharedData() {
            return new SharedDataImplementations.SynchronizedHashMapShared();
        }
    }
}
