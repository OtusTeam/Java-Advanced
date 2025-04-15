package com.otus.java.advanced.blocking;

import com.otus.java.advanced.common.CleanUpProcessor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BlockingChannel {
    public static void main(String[] args) {
        String filePath = "example.txt";

        // Запись данных в файл
        try (FileOutputStream fos = new FileOutputStream(filePath);
             FileChannel fileChannel = fos.getChannel()) {

            String data = "Hello, NIO Blocking Channel!";
            ByteBuffer buffer = ByteBuffer.allocate(1);
            buffer.clear();
            buffer.put(data.getBytes());
            buffer.flip(); // Переключаемся в режим чтения

            while (buffer.hasRemaining()) {
                fileChannel.write(buffer);// Запись данных в файл
                System.out.println(buffer.position());
            }

            System.out.printf("Данные записаны в файл: %s \n", filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Чтение данных из файла
        try (FileInputStream fis = new FileInputStream(filePath);
             FileChannel fileChannel = fis.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int bytesRead = fileChannel.read(buffer); // Чтение данных из файла

            if (bytesRead != -1) {
                buffer.flip(); // Переключаемся в режим чтения
                byte[] data = new byte[bytesRead];
                buffer.get(data); // Получаем данные из буфера
                System.out.printf("Данные, прочитанные из файла: %s \n", new String(data));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //CleanUpProcessor.deleteFile("example.txt");
    }
}
