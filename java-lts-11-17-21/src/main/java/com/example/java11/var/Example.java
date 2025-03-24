package com.example.java11.var;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Example {

    public static void main(String[] args) throws Exception {
        var list = new ArrayList<String>();

        var newList = List.of("hello", "otus");
        newList.forEach(System.out::println);

        String fileName = "./pom.xml";
        var i = 0;
        var str = "";
        var path = Paths.get(fileName);
        var bytes = Files.readAllBytes(path);

        System.out.println("bytes: " + bytes);

        for (var b : bytes) {
            // TODO
        }

        try (var foo = new FileInputStream("")) {
            System.out.println(foo);
        } catch (Exception e) {
            // ignore
        }
    }
}
