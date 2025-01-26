package com.otus.java.advanced;

import java.io.*;

public class FilesProcessorIO {
    public static void mergeFilesWithIO(String[] filePaths, String outputFilePath) {
        try (FileOutputStream outputStream = new FileOutputStream(outputFilePath);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
            for (String filePath : filePaths) {
                try (FileInputStream inputStream = new FileInputStream(filePath);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                        bufferedOutputStream.write(buffer, 0, bytesRead);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        File file1 = new File("java-nio/src/com/otus/java/advanced/input/file1.txt");
        File file2 = new File("java-nio/src/com/otus/java/advanced/input/file2.txt");
        File file3 = new File("java-nio/src/com/otus/java/advanced/input/file3.txt");
        String[] inputFilePaths = {file1.getPath(), file2.getPath(), file3.getPath()};
        File output = new File("java-nio/src/com/otus/java/advanced/output/mergedFileIO");
        String outputFilePath = output.getPath();
        mergeFilesWithIO(inputFilePaths, outputFilePath);
    }
}
