package com.otus.java.advanced;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;

public class JavaWindowsApi {
    public static void main(String[] args) {
        getSound();
        getSystemInfo();
    }

    public static void getSystemInfo() {
        System.out.println("\n=== 2. GetSystemInfo Example ===");

        try (Arena arena = Arena.ofConfined()) {
            Linker linker = Linker.nativeLinker();
            SymbolLookup kernel32 = SymbolLookup.libraryLookup("kernel32.dll", arena);

            // Находим GetSystemInfo
            MemorySegment getSystemInfoFunc = kernel32.find("GetSystemInfo")
                    .orElseThrow(() -> new RuntimeException("GetSystemInfo not found"));

            // Создаём структуру SYSTEM_INFO
            MemoryLayout systemInfoLayout = MemoryLayout.structLayout(
                    ValueLayout.JAVA_INT.withName("dwOemId"),
                    ValueLayout.JAVA_INT.withName("dwPageSize"),
                    ValueLayout.ADDRESS.withName("lpMinimumApplicationAddress"),
                    ValueLayout.ADDRESS.withName("lpMaximumApplicationAddress"),
                    ValueLayout.JAVA_LONG.withName("dwActiveProcessorMask"),
                    ValueLayout.JAVA_INT.withName("dwNumberOfProcessors"),
                    ValueLayout.JAVA_INT.withName("dwProcessorType"),
                    ValueLayout.JAVA_INT.withName("dwAllocationGranularity"),
                    ValueLayout.JAVA_SHORT.withName("wProcessorLevel"),
                    ValueLayout.JAVA_SHORT.withName("wProcessorRevision")
            );

            // Выделяем память для структуры
            MemorySegment systemInfo = arena.allocate(systemInfoLayout);

            // Дескриптор функции: void GetSystemInfo(LPSYSTEM_INFO lpSystemInfo);
            FunctionDescriptor descriptor = FunctionDescriptor.ofVoid(
                    ValueLayout.ADDRESS  // указатель на SYSTEM_INFO
            );

            MethodHandle getSystemInfoHandle = linker.downcallHandle(
                    getSystemInfoFunc, descriptor
            );

            // Вызываем функцию
            getSystemInfoHandle.invokeExact(systemInfo);

            // Читаем значения из структуры
            VarHandle pageSizeHandle = systemInfoLayout.varHandle(
                    MemoryLayout.PathElement.groupElement("dwPageSize")
            );
            VarHandle numProcessorsHandle = systemInfoLayout.varHandle(
                    MemoryLayout.PathElement.groupElement("dwNumberOfProcessors")
            );

            int pageSize = (int) pageSizeHandle.get(systemInfo);
            int numProcessors = (int) numProcessorsHandle.get(systemInfo);

            System.out.println("System Page Size: " + pageSize + " bytes");
            System.out.println("Number of Processors: " + numProcessors);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static void getSound() {
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
