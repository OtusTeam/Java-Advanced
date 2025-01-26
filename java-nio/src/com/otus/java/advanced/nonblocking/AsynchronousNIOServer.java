package com.otus.java.advanced.nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AsynchronousNIOServer {

    public static void main(String[] args) {
        try {
            // Создаем AsynchronousServerSocketChannel
            AsynchronousServerSocketChannel serverChannel =
                    AsynchronousServerSocketChannel.open();

            // Привязываем сервер к порту 8080
            serverChannel.bind(new InetSocketAddress(8082));
            System.out.println("Асинхронный сервер запущен на порту 8082...");

            // Ожидаем соединения (асинхронно)
            serverChannel.accept(null, new CompletionHandler<>() {

                @Override
                public void completed(AsynchronousSocketChannel clientChannel, Object attachment) {
                    try {
                        System.out.println("Новое соединение: " + clientChannel.getRemoteAddress());

                        // Готовим буфер для чтения данных от клиента
                        ByteBuffer buffer = ByteBuffer.allocate(1024);

                        // Читаем данные из клиентского канала (асинхронно)
                        clientChannel.read(buffer, buffer, new java.nio.channels.CompletionHandler<>() {
                            @Override
                            public void completed(Integer result, ByteBuffer attachment) {
                                // Переключаем буфер в режим чтения
                                attachment.flip();
                                byte[] data = new byte[attachment.remaining()];
                                attachment.get(data);

                                String message = new String(data);
                                System.out.println("Сообщение от клиента: " + message);

                                // Отправляем ответ клиенту
                                ByteBuffer responseBuffer = ByteBuffer.wrap(("Эхо: " + message).getBytes());
                                clientChannel.write(responseBuffer, responseBuffer, new CompletionHandler<>() {
                                    @Override
                                    public void completed(Integer result, ByteBuffer attachment) {
                                        System.out.println("Ответ отправлен клиенту.");
                                        try {
                                            clientChannel.close(); // Закрываем соединение
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void failed(Throwable exc, ByteBuffer attachment) {
                                        System.out.println("Ошибка при отправке данных клиенту: " + exc.getMessage());
                                    }
                                });
                            }

                            @Override
                            public void failed(Throwable exc, ByteBuffer attachment) {
                                System.err.println("Ошибка при чтении данных: " + exc.getMessage());
                                try {
                                    clientChannel.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        // Готовимся принимать следующее соединение
                        serverChannel.accept(null, this);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.err.println("Ошибка при принятии соединения: " + exc.getMessage());
                }
            });

            // Сохраняем основной поток активным
            while (true) {
                Thread.sleep(1000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
