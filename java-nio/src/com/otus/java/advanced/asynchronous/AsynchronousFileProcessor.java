package com.otus.java.advanced.asynchronous;

import com.otus.java.advanced.common.CreateTestFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsynchronousFileProcessor {
    public static void main(String[] args) throws Exception
    {
        readFile();
    }

    private static void readFile()
            throws IOException, InterruptedException,
            ExecutionException
    {

        String filePath
                = "fileCopy.txt";
        CreateTestFile.createTestFile(filePath, 1000);
        //Сначала создаём файл, а затем указываем его
        printFileContents(filePath);

        Path path = Paths.get(filePath);
        AsynchronousFileChannel channel
                = AsynchronousFileChannel.open(
                path, StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(400);
        Future<Integer> result = channel.read(buffer, 0);

        //Проверяем, выполнена ли задача или
        // нет
        while (!result.isDone()) {
            System.out.println(
                    "The process of reading file is in progress asynchronously.");
        }

        // Распечатать и отобразить сообщения
        System.out.printf("Is the reading done? \n %s", result.isDone());
        System.out.printf("The number of bytes read from file is %s", result.get());

        buffer.flip();

        System.out.print("Buffer contents: ");

        while (buffer.hasRemaining()) {

            System.out.print((char)buffer.get());
        }

        System.out.println(" ");

        // Закрываем каналы с помощью метода close
        buffer.clear();
        channel.close();
    }

    private static void printFileContents(String path)
            throws IOException
    {

        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);

        String textRead = br.readLine();

        System.out.println("Content in the File: ");

        while (textRead != null) {

            // После прочтения всего текста из файла
            //    выводим количество байтов в файле
            System.out.printf("     %s", textRead);
            textRead = br.readLine();
        }

        // Закрываем каналы

        // Закрываем FileReader
        fr.close();
        // Закрываем BufferedReader
        br.close();
    }
}
