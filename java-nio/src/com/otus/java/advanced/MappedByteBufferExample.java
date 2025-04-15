package com.otus.java.advanced;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MappedByteBufferExample {

    public static void main(String[] args) {
        read();
        write();
    }

    public static void read() {
        // Открываем канал чтения файла
        File file = new File("src/com/otus/java/advanced/input/nio.txt");

        try (FileInputStream fis = new FileInputStream(file);
             FileChannel channel = fis.getChannel()) {
            // Создаем отображаемый буфер размера 8байт, начиная с позиции 1 байта
            int position = 0;
            long size = file.length();

            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, position, size);

            byte[] data = new byte[(int)size];
            buffer.get(data); // Читаем данные из буфера в массив

            System.out.println("Прочитано " + data.length + " байтов.");
            System.out.println(new String(data, StandardCharsets.UTF_8));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write() {
        try {
            String fileName = "src/com/otus/java/advanced/output/output.txt";
            // режим чтение-запись
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
            FileChannel channel = raf.getChannel();

            ByteBuffer writeBuffer = ByteBuffer.wrap("Привет мир!".getBytes());

            // Отображаем первые 100 байт файла для записи
            MappedByteBuffer mappedBuf = channel.map(FileChannel.MapMode.READ_WRITE, 0, 100);
            mappedBuf.put(writeBuffer); // записываем строку в буфер

            System.out.printf("Записали данные в файл %s. ", mappedBuf.position());

            raf.close(); // закрываем ресурс
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
