package com.example.java11.trywithresources;

import java.io.BufferedReader;
import java.io.FileReader;

public class Example {

    public static void main(String[] args) throws Exception {
        BufferedReader reader1 = new BufferedReader(new FileReader("./README.md"));
        try (reader1) {
            while (reader1.ready()) {
                System.out.println(reader1.readLine());
            }
        }
    }

}
