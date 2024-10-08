package com.otus.java.advanced.services.impl;

import com.otus.java.advanced.Main;
import com.otus.java.advanced.services.Filter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Main.class})
class FilterUppercaseImplTest {

    @Autowired
    Filter filterUppercase;

    @Test
    void step() {
        Assertions.assertEquals(1_000_000, filterUppercase.step());
        System.out.println("!!! OK !!!");
    }
}