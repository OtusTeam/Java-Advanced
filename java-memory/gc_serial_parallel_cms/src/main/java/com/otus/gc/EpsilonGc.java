package com.otus.gc;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EpsilonGc {

    //-XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC -Xms64m -Xmx64m -Xlog:gc*::time
    public static void main(String[] args)  {
        new Scanner(System.in).nextLine();
        System.out.println("start loop");

        for (int i = 200_000; i < 2_000_000; i++) {
            String newString = String.valueOf(i);
        }
        System.out.println("end loop");
    }
}
