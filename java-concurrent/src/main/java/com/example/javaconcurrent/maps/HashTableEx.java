package com.example.javaconcurrent.maps;

import java.util.Hashtable;

public class HashTableEx {

    public static void main(String[] args) {
        // Создание экземпляра Hashtable
        Hashtable<Integer, String> hashtable = new Hashtable<>();

        // Добавление элементов в Hashtable
        hashtable.put(1, "One");
        hashtable.put(2, "Two");
        hashtable.put(3, "Three");

        // Получение значения по ключу
        String value = hashtable.get(2);
        System.out.println("Value at key 2: " + value);

        // Проверка наличия ключа
        if (hashtable.containsKey(3)) {
            System.out.println("Hashtable contains key 3");
        }

        // Удаление элемента по ключу
        hashtable.remove(1);

        // Перебор элементов Hashtable
        for (Integer key : hashtable.keySet()) {
            String val = hashtable.get(key);
            System.out.println("Key: " + key + ", Value: " + val);
        }
    }
}
