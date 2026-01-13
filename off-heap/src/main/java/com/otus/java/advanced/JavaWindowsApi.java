package com.otus.java.advanced;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class JavaWindowsApi {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n=== 3. Beep Example ===");

        try (Arena arena = Arena.ofConfined()) {
            Linker linker = Linker.nativeLinker();
            SymbolLookup kernel32 = SymbolLookup.libraryLookup("kernel32.dll", arena);

            MemorySegment beepFunc = kernel32.find("Beep")
                    .orElseThrow(() -> new RuntimeException("Beep not found"));

            // BOOL Beep(DWORD dwFreq, DWORD dwDuration);
            FunctionDescriptor descriptor = FunctionDescriptor.of(
                    ValueLayout.JAVA_INT,   // BOOL
                    ValueLayout.JAVA_INT,   // DWORD dwFreq
                    ValueLayout.JAVA_INT    // DWORD dwDuration
            );

            MethodHandle beepHandle = linker.downcallHandle(beepFunc, descriptor);

            // Воспроизводим звук: 1000 Hz на 500 ms
            boolean success = (int) beepHandle.invokeExact(1000, 500) != 0;
            System.out.println("Beep played successfully: " + success);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
