package com.otus.java.advanced.nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NonBlockingNIOServer {
    public static void main(String[] args) throws IOException {
        var ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(9999));
        //Включаем неблокирующий режим канала
        ssc.configureBlocking(false);

        var responseMessage = "Привет от сервера! : " + ssc.socket().getLocalSocketAddress();
        var sendBuffer = ByteBuffer.wrap(responseMessage.getBytes());

        while (true) {
            //Ловим соединения через вызов ssc.accept().
            //Т.к. стоит неблокирующий режим, метод accept немедленно вернет null, если нет ожидающих подключений
            try (SocketChannel sc = ssc.accept()) {
                if (sc != null) {
                    System.out.println();
                    System.out.println("Принято соединение от  " + sc.socket().getRemoteSocketAddress());
                    var receivedBuffer = ByteBuffer.allocate(100);
                    sc.read(receivedBuffer);
                    var requestMessage = new String(receivedBuffer.array());
                    System.out.println(requestMessage);

                    sendBuffer.rewind();
                    sc.write(sendBuffer);
                } else {
                    System.out.print(".");
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
