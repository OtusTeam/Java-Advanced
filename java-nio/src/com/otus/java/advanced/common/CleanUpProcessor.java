package com.otus.java.advanced.common;

import java.io.File;

public class CleanUpProcessor {

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        // Проверяем, существует ли файл
        if (file.exists()) {
            // Пытаемся удалить файл
            if (file.delete()) {
                System.out.println(STR."Файл успешно удален: \{file.getAbsolutePath()}");
            } else {
                System.out.println(STR."Не удалось удалить файл: \{file.getAbsolutePath()}");
            }
        } else {
            System.out.println(STR."Файл не существует: \{file.getAbsolutePath()}");
        }
    }
}
