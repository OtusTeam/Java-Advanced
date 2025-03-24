package com.otus.java.advanced;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesProcessorNIO {
    public static void mergeFilesWithNIO(String[] filePaths, String outputFilePath) {

        try {
            Path outputPath = Paths.get(outputFilePath);
            for (String filePath : filePaths) {
                Files.write(outputPath, Files.readAllLines(Paths.get(filePath)), java.nio.file.StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String[] inputFilePaths = {"/input/file1.txt", "/input/file2.txt", "/input/file3.txt"};
        String outputFilePath = "/output/mergedFileNIO";
        mergeFilesWithNIO(inputFilePaths, outputFilePath);
    }
}
