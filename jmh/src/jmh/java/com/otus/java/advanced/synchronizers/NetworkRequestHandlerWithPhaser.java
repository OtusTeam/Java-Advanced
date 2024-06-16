package com.otus.java.advanced.synchronizers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Phaser;

public class NetworkRequestHandlerWithPhaser {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        // Создаем Phaser с количеством участников равным 1 (главный поток)
        Phaser phaser = new Phaser(1);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket);

            // Регистрируем нового участника в Phaser
            phaser.register();

            // Создаем новый поток для обработки запроса
            Thread requestHandlerThread = new Thread(() -> {
                handleRequest(clientSocket);
                // Сообщаем Phaser о завершении обработки запроса
                phaser.arriveAndDeregister();
            });
            requestHandlerThread.start();
        }
    }

    private static void handleRequest(Socket clientSocket) {
        try {
            // Имитация обработки запроса
            Thread.sleep(1000);

            // Отправляем ответ клиенту
            OutputStream outputStream = clientSocket.getOutputStream();
            String response = "HTTP/1.1 200 OK\n\nHello, World!";
            outputStream.write(response.getBytes());
            outputStream.flush();

            // Закрываем соединение
            clientSocket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
