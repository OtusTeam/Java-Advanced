package com.example.java11.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Map.entry;


public class Example {

    public static void main(String[] args) {

        List immutableList = List.of();
        var list = new ArrayList<String>();
        list.stream()
                .map((var s) -> s.toLowerCase())
                .collect(Collectors.toList());
        var foo = List.of("user", "github", "example");

        Map emptyImmutableMap = Map.of();

        // new HashMap<String, String>();
        var mmp = Map.of(2017, "s1", 2018, "s2");

        Map<Integer, String> emptyEntryMap = Map.ofEntries(
                entry(20, "装逼"),
                entry(30, "单身"),
                entry(40, "回家种地")
        );

        Map.Entry<String, String> immutableMapEntry = Map.entry("hi", "otus");
        Map.ofEntries(immutableMapEntry);

        Set<String> immutableSet = Set.of();

        Set<String> bar = Set.of("learn", "java", "11", "17", "21");
    }
}
