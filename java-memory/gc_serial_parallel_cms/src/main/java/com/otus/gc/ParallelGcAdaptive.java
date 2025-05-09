package com.otus.gc;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ParallelGcAdaptive {

    //-XX:+UseParallelGC -Xms526m -Xmx526m -XX:-UseAdaptiveSizePolicy -Xlog:gc*::time
    public static void main(String[] args) throws InterruptedException {
        Map<String, String> stringContainer = new HashMap<>();
        System.out.println("Start of program!");
        String stringWithPrefix = "stringWithPrefix";

        new Scanner(System.in).nextLine();
        System.out.println("start loop");

        for (int j = 0; j < 50; j++) {
            for (int i = 0; i < 500_000; i++) {
                String newString = stringWithPrefix + i;
                stringContainer.put(newString, newString);
            }
            for (int i = 0; i < 500_000; i++) {
                String newString = stringWithPrefix + i;
                stringContainer.remove(newString);
            }

            Thread.sleep(100);
        }

        System.out.println("end loop");

        new Scanner(System.in).nextLine();
        System.out.println("End of program!");
    }
}
