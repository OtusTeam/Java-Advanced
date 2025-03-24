package com.otus.java.advanced.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CreateTestFile {
    public static void createTestFile(String filePath, int size) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < size; i++) {
                writer.write("Hello, World!\n");
            }
        }
    }
}
