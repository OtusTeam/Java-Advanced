package com.otus.java.advanced.maps;

import com.otus.java.advanced.SharedData;

public class Experiment_4_ReadWriteLockedHashMapShared {

    public static class Experiment_4_ReadWriteLockedHashMapSharedSimple extends BaseExperimentSharedData.SimpleBenchmarks {

        @Override
        public SharedData buildSharedData() {
            return new SharedDataImplementations.ReadWriteLockedHashMapShared();
        }
    }

    public static class Experiment_4_ReadWriteLockedHashMapSharedGrouped extends BaseExperimentSharedData.GroupedBenchmarks {

        @Override
        public SharedData buildSharedData() {
            return new SharedDataImplementations.ReadWriteLockedHashMapShared();
        }
    }
}
