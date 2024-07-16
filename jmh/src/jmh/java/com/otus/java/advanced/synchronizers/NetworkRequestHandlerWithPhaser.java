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

        Phaser phaser = new Phaser(1);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket);

            phaser.register();

            Thread requestHandlerThread = new Thread(() -> {
                handleRequest(clientSocket);
                phaser.arriveAndDeregister();
            });
            requestHandlerThread.start();
        }
    }

    private static void handleRequest(Socket clientSocket) {
        try {
            Thread.sleep(1000);

            OutputStream outputStream = clientSocket.getOutputStream();
            String response = "HTTP/1.1 200 OK\n\nHello, World!";
            outputStream.write(response.getBytes());
            outputStream.flush();

            clientSocket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
