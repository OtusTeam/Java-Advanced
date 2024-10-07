package com.otus.java.advanced.jdkinstruments.jlink;

import java.util.logging.Logger;

// compile and run
// javac -d jlink-target .\src\main\java\module-info.java
// javac -d jlink-target --module-path jlink-target .\src\main\java\com\otus\java\advanced\jdkinstruments\jlink\Jlink.java
// java --module-path jlink-target --module java.advanced.course.jdk.instruments.jlink/com.otus.java.advanced.jdkinstruments.jlink.Jlink

// use jdeps to view dependencies
// jdeps --module-path jlink-target -s --module java.advanced.course.jdk.instruments.jlink

// create own lightweight jre
// jlink [options] –module-path modulepath –add-modules module [, module…] --output <target-directory>
// jlink --module-path "C:\Users\SYBERIAN\.jdks\openjdk-21.0.1\jmods;C:\Users\SYBERIAN\Desktop\Java-Advanced\jdk-instruments\jlink-target" --add-modules java.advanced.course.jdk.instruments.jlink --output jlink-customjre
// jlink --launcher customjrelauncher=java.advanced.course.jdk.instruments.jlink/com.otus.java.advanced.jdkinstruments.jlink.Jlink --module-path "C:\Users\SYBERIAN\Desktop\Java-Advanced\jdk-instruments\jlink-target;C:\Users\SYBERIAN\.jdks\openjdk-21.0.1\jmods" --add-modules java.advanced.course.jdk.instruments.jlink --output jlink-customjre

// run jre - use a second command with customjrelauncher
// 1)
// next command provide an: Error occurred during initialization of boot layer java.lang.module.FindException: Module java.advanced.course.jdk.instruments.jlink\com.otus.java.advanced.jdkinstruments.jlink.Jlink not found
// java -classpath .\jlink-customjre\bin --module java.advanced.course.jdk.instruments.jlink\com.otus.java.advanced.jdkinstruments.jlink.Jlink
// 2)
// please use this command instead of previous
// .\jlink-customjre\bin\customjrelauncher.bat
public class Jlink {

    private static final Logger LOG = Logger.getLogger(Jlink.class.getName());
    public static void main(String[] args) {
        LOG.info("JLink example");
    }
}
