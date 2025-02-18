package com.example.javamemory;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

public class Phantom {
    private static class MyPhantom extends PhantomReference<String> {

        private String name;

        public MyPhantom(String referent, ReferenceQueue<? super String> q, String name) {
            super(referent, q);
            this.name = name;
        }

        @Override
        public String get() {
            return name;
        }
    }

    private static class PhantomStorage {

        private ReferenceQueue<String> queue = new ReferenceQueue<>();

        private List<MyPhantom> phantoms = new LinkedList<>();

        public void add(String someData) {
            MyPhantom phantom = new MyPhantom(someData, queue, "my ref");
            phantoms.add(phantom);
        }

        public void utilizeResource() {
            for (ListIterator<MyPhantom> i = phantoms.listIterator(); i.hasNext();) {
                MyPhantom current = i.next();
                if (current != null && current.isEnqueued()) {
                    System.out.println("Utilized " + current.get());
                    current.clear();
                    i.remove();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PhantomStorage storage = new PhantomStorage();
        String data = "123".repeat(1000);
        storage.add(data);
        data = null;
        System.gc();
        TimeUnit.SECONDS.sleep(3);
        storage.utilizeResource();
    }

    //Создаем хранилище. В нем есть очередь, которая необходима для ссылок.
    //Но эта очередь read-only, поэтому создаем свой список и в него помещаем наши фантомные ссылки.
    //Когда вызывается метод для утилизации ресурсов, мы проверяем, есть ли ссылка в очереди,
    //т.е. помечен ли объект на удаление.
    //Далее вызываем явно метод clear(),
   //чтобы указать GC, что нужно удалить объект в будущем и удаляем из нашего списка.
}
