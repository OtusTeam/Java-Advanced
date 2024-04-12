package com.otus.java.advanced.bytebuddy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ExampleServiceTest {
    private static final String ABRAKADABRA = "abrakadabra";

    @Autowired
    private ExampleService exampleService;

    @Test
    void should_throw_logger_exception_when_call_doSomething_method() {
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class,
                () -> exampleService.doSomethingWithError(ABRAKADABRA)
        );
        assertEquals("throw error due to logger error show is: true", runtimeException.getMessage());
    }

    @Test
    void should_return_testValue_when_call_doSomething_method() {
        assertEquals(ABRAKADABRA, exampleService.doSomething(ABRAKADABRA));
    }
}
