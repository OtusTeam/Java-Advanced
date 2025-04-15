package com.otus.java.advanced.common;

import java.io.File;

public class CleanUpProcessor {

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        // Проверяем, существует ли файл
        if (file.exists()) {
            // Пытаемся удалить файл
            if (file.delete()) {
                System.out.printf("Файл успешно удален: %s \n", file.getAbsolutePath());
            } else {
                System.out.printf("Не удалось удалить файл: %s \n", file.getAbsolutePath());
            }
        } else {
            System.out.printf("Файл не существует: %s", file.getAbsolutePath());
        }
    }
}
