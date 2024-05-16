package com.otus.java.advanced.jdkinstruments.jdeps;

// javac -d jdeps-target .\src\main\java\com\otus\java\advanced\jdkinstruments\jdeps\*.java

// jar cf .\jdeps-output\JDeps.jar .\jdeps-target\com\otus\java\advanced\jdkinstruments\jdeps\*.class
// jar cfe .\jdeps-output\JDeps.jar com.otus.java.advanced.jdkinstruments.jdeps.JDeps .\jdeps-target\com\otus\java\advanced\jdkinstruments\jdeps\*.class

// jdeps .\jdeps-output\JDeps.jar
public class JDeps {

    public static void main(String[] args) {
        printGreeting("JDeps demo");
    }

    @Deprecated
    private static void printGreeting(String greeting) {
        System.out.println(greeting);

        System.out.println(new Double("1"));
    }
}
