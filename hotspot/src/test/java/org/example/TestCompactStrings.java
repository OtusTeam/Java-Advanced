package org.example;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

public class TestCompactStrings {

    @Test
    public void test() throws IOException {
        final Runtime runtime = Runtime.getRuntime();
        final DecimalFormat format = new DecimalFormat("#,###");
        final String file = IOUtils.resourceToString("book-war-and-peace.txt",
                StandardCharsets.UTF_8,
                TestCompactStrings.class.getClassLoader());

        System.out.println("File length: " + format.format(file.length()));
        System.out.println("Free memory: " + format.format(runtime.freeMemory()));
    }
}
