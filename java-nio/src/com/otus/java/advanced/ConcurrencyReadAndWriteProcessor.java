package com.otus.java.advanced;

import com.otus.java.advanced.common.CleanUpProcessor;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyReadAndWriteProcessor {
    private static final String FILE_PATH = "testfile.txt";
    private static final int FILE_SIZE = 10_000_000; // 10 MB
    private static final int THREAD_COUNT = 3;

    public static void main(String[] args) throws IOException {
        // Создаем тестовый файл
        createTestFile(FILE_PATH, FILE_SIZE);

        // Измеряем время выполнения IO
        long ioStartTime = System.currentTimeMillis();
        executeIoThreads();
        long ioEndTime = System.currentTimeMillis();
        System.out.printf("IO time: %s ms \n", ioEndTime - ioStartTime);

        // Измеряем время выполнения NIO
        long nioStartTime = System.currentTimeMillis();
        executeNioThreads();
        long nioEndTime = System.currentTimeMillis();
        System.out.printf("NIO time: %s ms \n", nioEndTime - nioStartTime);
        CleanUpProcessor.deleteFile(FILE_PATH);
        for(int i = 1; i <= THREAD_COUNT; i++) {
            CleanUpProcessor.deleteFile(String.format("io_output_pool-1-thread-%s.txt", i));
            CleanUpProcessor.deleteFile(String.format("nio_output_pool-2-thread-%s.txt", i));
        }
    }

    private static void createTestFile(String filePath, int size) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < size; i++) {
                writer.write("Hello, World!\n");
            }
        }
    }

    private static void executeIoThreads() {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.execute(() -> {
                try {
                    ioReadWrite(FILE_PATH, "io_output_" + Thread.currentThread().getName() + ".txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {}
    }

    private static void ioReadWrite(String filePath, String outputFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }
        }
    }

    private static void executeNioThreads() {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.execute(() -> {
                try {
                    nioReadWrite(FILE_PATH, "nio_output_" + Thread.currentThread().getName() + ".txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {}
    }

    private static void nioReadWrite(String filePath, String outputFilePath) throws IOException {
        try (FileChannel fileChannel = FileChannel.open(Paths.get(filePath), StandardOpenOption.READ);
             FileChannel outputChannel = FileChannel.open(Paths.get(outputFilePath), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (fileChannel.read(buffer) > 0) {
                buffer.flip();
                outputChannel.write(buffer);
                buffer.clear();
            }
        }
    }
}
