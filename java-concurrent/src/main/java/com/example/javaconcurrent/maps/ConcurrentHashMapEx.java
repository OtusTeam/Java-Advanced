package com.example.javaconcurrent.maps;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapEx {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        // Добавление элементов в ConcurrentHashMap
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);

        // Получение значения по ключу
        int value = map.get("Two");
        System.out.println("Value for key 'Two': " + value);

        // Проверка наличия ключа
        boolean containsKey = map.containsKey("Three");
        System.out.println("Contains key 'Three': " + containsKey);

        // Удаление элемента по ключу
        int removedValue = map.remove("One");
        System.out.println("Removed value for key 'One': " + removedValue);

        // Размер ConcurrentHashMap
        int size = map.size();
        System.out.println("Size of ConcurrentHashMap: " + size);
    }
}
