plugins {
    id("java")
    id("me.champeau.jmh") version "0.7.2"
}

group = "com.otus.java.advanced"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    jmh("com.google.guava:guava:33.0.0-jre")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

// lesson These parameters overrides parameters from annotations
jmh {
    excludes.addAll("*")
//    includes.addAll("com.otus.java.advanced.FibonacciBenchmarks.fibClassicDefault")
//    includes.addAll("com.otus.java.advanced.FibonacciBenchmarks.fibClassicShort")
//    includes.addAll("com.otus.java.advanced.FibonacciBenchmarks.fibClassicNoFork")
//    includes.addAll("com.otus.java.advanced.FibonacciBenchmarks.fibClassicTracing")
//    includes.addAll("com.otus.java.advanced.FibonacciBenchmarks.fibClassicRecursive*")
//    includes.addAll("com.otus.java.advanced.FibonacciBenchmarks.fibClassicSpeedUp*")
//    includes.addAll("com.otus.java.advanced.FibonacciBenchmarks.fibClassicCnt")
//    includes.addAll("com.otus.java.advanced.FibonacciBenchmarks.fibClassicModeAll")

//    includes.addAll("com.otus.java.advanced.DeadCodeElimination")

//    includes.addAll("com.otus.java.advanced.ConstantFolding")

//    includes.addAll("com.otus.java.advanced.map.ConcurrentHashMapEx.benchmarkConcurrentHashMapGet")
//    includes.addAll("com.otus.java.advanced.map.ConcurrentHashMapEx.benchmarkConcurrentHashMapPut")
//    includes.addAll("com.otus.java.advanced.map.HashTableEx.benchmarkHashtablePut")
//    includes.addAll("com.otus.java.advanced.map.ConcurrentSkipListMapEx.benchmarkConcurrentSkipListMapPut")

//    includes.addAll("com.otus.java.advanced.counters.Experiment_1_NoThreadSafeCounter")
//    includes.addAll("com.otus.java.advanced.counters.Experiment_2_VolatileNoThreadSafeCounter")
//    includes.addAll("com.otus.java.advanced.counters.Experiment_3_SynchronizedCounter")
//    includes.addAll("com.otus.java.advanced.counters.Experiment_4_LockedCounter")
//    includes.addAll("com.otus.java.advanced.counters.Experiment_5_ReadWriteLockedCounter")
//    includes.addAll("com.otus.java.advanced.counters.Experiment_6_AtomicCounter")
//    includes.addAll("com.otus.java.advanced.counters.*")

//    includes.addAll("com.otus.java.advanced.maps.Experiment_1_HashMapShared")
//    includes.addAll("com.otus.java.advanced.maps.Experiment_2_SynchronizedHashMapShared")
//    includes.addAll("com.otus.java.advanced.maps.Experiment_3_LockedHashMapShared")
//    includes.addAll("com.otus.java.advanced.maps.Experiment_4_ReadWriteLockedHashMapShared")
    includes.addAll("com.otus.java.advanced.maps.Experiment_5_ConcurrentHashMapShared")

// lesson Parameters below are for gradle DSL. So they must be converted to kotlin DSL
//    includes = ['some regular expression'] // include pattern (regular expression) for benchmarks to be executed
//    excludes = ['some regular expression'] // exclude pattern (regular expression) for benchmarks to be executed
//    iterations = 10 // Number of measurement iterations to do.
//    benchmarkMode = ['thrpt','ss'] // Benchmark mode. Available modes are: [Throughput/thrpt, AverageTime/avgt, SampleTime/sample, SingleShotTime/ss, All/all]
//    batchSize = 1 // Batch size: number of benchmark method calls per operation. (some benchmark modes can ignore this setting)
//    fork = 2 // How many times to forks a single benchmark. Use 0 to disable forking altogether
//    failOnError = false // Should JMH fail immediately if any benchmark had experienced the unrecoverable error?
//    forceGC = false // Should JMH force GC between iterations?
//    jvm = 'myjvm' // Custom JVM to use when forking.
//    jvmArgs = ['Custom JVM args to use when forking.']
//    jvmArgsAppend = ['Custom JVM args to use when forking (append these)']
//    jvmArgsPrepend =[ 'Custom JVM args to use when forking (prepend these)']
//    humanOutputFile = project.file("${project.buildDir}/reports/jmh/human.txt") // human-readable output file
//    resultsFile = project.file("${project.buildDir}/reports/jmh/results.txt") // results file
//    operationsPerInvocation = 10 // Operations per invocation.
//    benchmarkParameters =  [:] // Benchmark parameters.
//    profilers = [] // Use profilers to collect additional data. Supported profilers: [cl, comp, gc, stack, perf, perfnorm, perfasm, xperf, xperfasm, hs_cl, hs_comp, hs_gc, hs_rt, hs_thr, async]
//    timeOnIteration = '1s' // Time to spend at each measurement iteration.
//    resultFormat = 'CSV' // Result format type (one of CSV, JSON, NONE, SCSV, TEXT)
//    synchronizeIterations = false // Synchronize iterations?
//    threads = 4 // Number of worker threads to run with.
//    threadGroups = [2,3,4] //Override thread group distribution for asymmetric benchmarks.
//    jmhTimeout = '1s' // Timeout for benchmark iteration.
//    timeUnit = 'ms' // Output time unit. Available time units are: [m, s, ms, us, ns].
//    verbosity = 'NORMAL' // Verbosity mode. Available modes are: [SILENT, NORMAL, EXTRA]
//    warmup = '1s' // Time to spend at each warmup iteration.
//    warmupBatchSize = 10 // Warmup batch size: number of benchmark method calls per operation.
//    warmupForks = 0 // How many warmup forks to make for a single benchmark. 0 to disable warmup forks.
//    warmupIterations = 1 // Number of warmup iterations to do.
//    warmupMode = 'INDI' // Warmup mode for warming up selected benchmarks. Warmup modes are: [INDI, BULK, BULK_INDI].
//    warmupBenchmarks = ['.*Warmup'] // Warmup benchmarks to include in the run in addition to already selected. JMH will not measure these benchmarks, but only use them for the warmup.

//    zip64 = true // Use ZIP64 format for bigger archives
//    jmhVersion = '1.37' // Specifies JMH version
//    includeTests = true // Allows to include test sources into generate JMH jar, i.e. use it when benchmarks depend on the test classes.
//    duplicateClassesStrategy = DuplicatesStrategy.FAIL // Strategy to apply when encountring duplicate classes during creation of the fat jar (i.e. while executing jmhJar task)
}
