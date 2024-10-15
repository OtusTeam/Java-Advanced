package com.otus.java.advanced.services;

import com.otus.java.advanced.services.impl.CounterUppercaseImpl;
import com.otus.java.advanced.services.impl.FilterUppercaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Disabled("Disabled graalvm doesn't support mockito")
@ExtendWith(SpringExtension.class)
class CounterTest {

    @InjectMocks
    CounterUppercaseImpl service;

    @Mock
    FilterUppercaseImpl filterUppercase;

    @Test
    void count() {

        Mockito.when(filterUppercase.is(83))
                .thenReturn(Boolean.TRUE);

        Mockito.when(filterUppercase.count())
                .thenReturn(10);

        Mockito.when(filterUppercase.step())
                .thenReturn(1);

        var total = service.count("S");

        Assertions.assertEquals(9, total);
    }
}