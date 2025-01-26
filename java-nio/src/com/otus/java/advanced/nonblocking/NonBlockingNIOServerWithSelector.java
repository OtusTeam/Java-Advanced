package com.otus.java.advanced.nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NonBlockingNIOServerWithSelector {
    public static void main(String[] args) throws IOException {
        try (ServerSocketChannel channel = ServerSocketChannel.open();
             //Открытие селектора. Под капотом вызывается SelectorProvider, реализация которого является платформозависимой
            Selector selector = Selector.open()) {
            channel.socket().bind(new InetSocketAddress(8081));
            channel.configureBlocking(false);
            //Регистрируем серверный канал в селекторе с интересующим типом операции - принятие подключения
            SelectionKey registeredKey = channel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Сервер запущен на порту 8081");

            while (channel.isOpen()) {
                //Получаем количество готовых к обработке каналов.
                int numReadyChannels = selector.select();
                if (numReadyChannels == 0) {
                    continue;
                }
                //Получаем готовые к обработке каналы
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                //Обрабатываем каналы в соответствии с типом доступной каналу операции
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();

                    if (key.isAcceptable()) {
                        //Принятие подключения серверным сокетом
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        if (client == null) {
                            continue;
                        }
                        client.configureBlocking(false);
                        //Регистрируем принятое подключение в селекторе с интересующим типом операции - чтение
                        client.register(selector, SelectionKey.OP_READ, "unknown");
                        System.out.println("Новый клиент подключился");
                    }

                    if (key.isReadable()) {
                        //Тут происходит обработка принятых подключений
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
                        int r = client.read(requestBuffer);
                        if (r == -1) {
                            client.close();
                        } else {
                            //В этом блоке происходит обработка запроса
                            String requestMessage =  new String(requestBuffer.array(), 0, requestBuffer.position());
                            System.out.println(requestMessage);
                            String responseMessage = "Привет от сервера! : " + client.socket().getLocalSocketAddress();
                            //Несмотря на то, что интересующая операция, переданная в селектор - чтение, мы все равно можем писать в сокет
                            client.write(ByteBuffer.wrap(responseMessage.getBytes()));
                            System.out.println(responseMessage);
                        }
                    }
                    //Удаляем ключ после обработки. Если канал снова будет доступным, его ключ снова появится в selectedKeys
                    keyIterator.remove();
                }
            }
        }
    }
}
