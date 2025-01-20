package com.otus.java.advanced;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.time.Duration;

public class SimpleAllocate {
    // -XX:NativeMemoryTracking=summary
    // найти идентификатор процесса jps -l
    // jcmd <pid> VM.native_memory summary
    //
    // отобразить по времени
    // jcmd <pid> VM.native_memory baseline
    // jcmd <pid> VM.native_memory summary.diff
    public static void main(String[] args) throws InterruptedException {
        String s = "My string";
        try (Arena arena = Arena.ofConfined()) {

            // Allocate off-heap memory
            MemorySegment nativeText = arena.allocateUtf8String(s);


            // Access off-heap memory
            for (int i = 0; i < s.length(); i++ ) {
                System.out.print(
                        (char) nativeText.get(ValueLayout.JAVA_BYTE, i)
                );
            }
            Thread.sleep(Duration.ofMinutes(1));

            // Java Heap +1953126KB
            MemorySegment allocatedMemory = arena.allocate(2000000000);
            System.out.println("allocate memory");

            Thread.sleep(Duration.ofMinutes(5));
        } // Off-heap memory is deallocated
    }


}