package com.otus.java.advanced.maps;

import com.otus.java.advanced.SharedData;

public class Experiment_1_HashMapShared {

    public static class Experiment_1_HashMapSharedSimple extends BaseExperimentSharedData.SimpleBenchmarks {

        @Override
        public SharedData buildSharedData() {
            return new SharedDataImplementations.HashMapShared();
        }
    }

    public static class Experiment_1_HashMapSharedGrouped extends BaseExperimentSharedData.GroupedBenchmarks {

        @Override
        public SharedData buildSharedData() {
            return new SharedDataImplementations.HashMapShared();
        }
    }
}