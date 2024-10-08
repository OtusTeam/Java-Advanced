package com.otus.java.advanced.services;

public interface Filter {
    Boolean is(int code);
    Integer count();
    Integer step();
}
