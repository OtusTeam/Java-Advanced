package com.example.javaconcurrent.maps;

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapEx {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // Добавление элементов в ConcurrentSkipListMap
        map.put(1, "One");
        map.put(3, "Three");
        map.put(2, "Two");

        // Получение значения по ключу
        String value = map.get(2);
        System.out.println("Value for key 2: " + value);

        // Проверка наличия ключа
        boolean containsKey = map.containsKey(3);
        System.out.println("Contains key 3: " + containsKey);

        // Удаление элемента по ключу
        String removedValue = map.remove(1);
        System.out.println("Removed value for key 1: " + removedValue);

        // Размер ConcurrentSkipListMap
        int size = map.size();
        System.out.println("Size of ConcurrentSkipListMap: " + size);
    }
}
