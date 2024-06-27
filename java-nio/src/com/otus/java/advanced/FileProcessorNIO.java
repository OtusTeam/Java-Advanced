package com.otus.java.advanced;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileProcessorNIO {
    public static void processFileWithNIO(String filePath) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        try {
            Files.lines(Paths.get(filePath))
                    .forEach(line -> executor.submit(() -> System.out.println(Thread.currentThread().getName() + ": " + line)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    public static void main(String[] args) {
        Path path = Paths.get("C:\\Users\\DANILOV\\IdeaProjects\\Java-Advanced\\java-nio\\src\\com\\otus\\java\\advanced\\input\\example.txt");
        processFileWithNIO(path.toString());
    }
}
