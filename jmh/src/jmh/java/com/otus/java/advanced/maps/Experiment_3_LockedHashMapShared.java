package com.otus.java.advanced.maps;

import com.otus.java.advanced.SharedData;

public class Experiment_3_LockedHashMapShared {

    public static class Experiment_3_LockedHashMapSharedSimple extends BaseExperimentSharedData.SimpleBenchmarks {

        @Override
        public SharedData buildSharedData() {
            return new SharedDataImplementations.LockedHashMapShared();
        }
    }

    public static class Experiment_3_LockedHashMapSharedGrouped extends BaseExperimentSharedData.GroupedBenchmarks {

        @Override
        public SharedData buildSharedData() {
            return new SharedDataImplementations.LockedHashMapShared();
        }
    }
}
