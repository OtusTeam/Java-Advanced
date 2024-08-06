package com.otus.java.advanced.bytebuddy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ExampleServiceImplTest {

    @Autowired
    private ExampleServiceImpl exampleServiceImpl;

    @Test
    void should_throw_logger_exception_when_call_reverse_method() {
        assertThrows(
                NullPointerException.class,
                () -> exampleServiceImpl.reverse(null)
        );
    }

    @Test
    void should_return_testValue_when_call_reverse_method() {
        assertEquals("gniP", exampleServiceImpl.reverse("Ping"));
    }
}
