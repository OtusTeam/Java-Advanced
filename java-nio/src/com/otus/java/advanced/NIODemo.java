package com.otus.java.advanced;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NIODemo {
    public static void main(String[] args) throws Exception {
        int count;
        try (SeekableByteChannel byteChannel = Files.newByteChannel(Paths.get("src/com/otus/java/advanced/input/nio.txt"))) {
//            ByteBuffer buffer = ByteBuffer.allocate(1024);
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            System.out.printf("position: %s%n", buffer.position());
            do {
                count = byteChannel.read(buffer);
                if (count != -1) {
                    System.out.printf("do while position: %s%n", buffer.position());
                    buffer.rewind();
                    System.out.printf("do while position: %s%n", buffer.position());
                    for (int i = 0; i < count; i++) {
                        System.out.print((char) buffer.get());
                    }
                }
            } while (count != -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
