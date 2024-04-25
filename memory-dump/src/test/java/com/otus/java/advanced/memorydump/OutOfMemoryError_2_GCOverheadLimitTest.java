package com.otus.java.advanced.memorydump;

import java.util.ArrayList;
import java.util.List;

/**
 * According to Java docs, by default, the JVM is configured to throw this error if the Java process
 * spends more than 98% of its time doing GC and when only less than 2% of the heap is recovered in each run.
 * <p>
 * cd ./memory-dump/src/test/java/com/otus/java/advanced/memorydump/
 * java ./OutOfMemoryError_2_GCOverheadLimitTest.java
 * <p>
 * java -Xms10m -Xmx10m -XX:+UseParallelGC ./OutOfMemoryError_2_GCOverheadLimitTest.java
 * java -Xms10m -Xmx10m -XX:+UseParallelGC -XX:-UseGCOverheadLimit ./OutOfMemoryError_2_GCOverheadLimitTest.java
 * java -Xms10m -Xmx10m -XX:+UseParallelGC -XX:+HeapDumpOnOutOfMemoryError ./OutOfMemoryError_2_GCOverheadLimitTest.java
 * <p>
 * java -Xms10m -Xmx10m -XX:+UseSerialGC ./OutOfMemoryError_2_GCOverheadLimitTest.java
 * java -Xms10m -Xmx10m -XX:+UseParallelGC ./OutOfMemoryError_2_GCOverheadLimitTest.java
 * java -Xms10m -Xmx10m -XX:+UseG1GC ./OutOfMemoryError_2_GCOverheadLimitTest.java
 * java -Xms256m -Xmx256m -XX:+UseZGC ./OutOfMemoryError_2_GCOverheadLimitTest.java
 * java -Xms256m -Xmx256m -XX:+UseConcMarkSweepGC ./OutOfMemoryError_2_GCOverheadLimitTest.java  - https://openjdk.org/jeps/363
 * <p>
 * -verbose:gc -XX:+PrintGCDetails
 */
public class OutOfMemoryError_2_GCOverheadLimitTest {

    //@Test
    void test() {
        List<MyBusinessEntity> list = new ArrayList<>();
        int count = 0;
        while (true) {
            if (count % 100 == 0) {
                System.out.println(++count);
            }
            list.add(new MyBusinessEntity(count,
                                          "name " + count,
                                          "address " + count,
                                          "gender " + count
            ));
            count++;
        }
    }

    public static void main(String[] args) {
        new OutOfMemoryError_2_GCOverheadLimitTest().test();
    }

    private static class MyBusinessEntity {
        private long id;
        private String name;
        private String address;
        private String gender;

        public MyBusinessEntity(long id, String name, String address, String gender) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.gender = gender;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}
