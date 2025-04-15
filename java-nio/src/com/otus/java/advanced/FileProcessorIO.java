package com.otus.java.advanced;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessorIO {
    public static void processFileWithIO(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File file = new File("src/com/otus/java/advanced/input/example.txt");
        String filePath = file.getAbsolutePath();
        processFileWithIO(filePath);
    }
}

