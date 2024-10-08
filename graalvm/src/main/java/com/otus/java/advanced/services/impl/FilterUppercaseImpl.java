package com.otus.java.advanced.services.impl;

import com.otus.java.advanced.services.Filter;
import org.springframework.stereotype.Service;

@Service
public class FilterUppercaseImpl implements Filter {
    @Override
    public Boolean is(int code) {
        return Character.isUpperCase(code);
    }

    @Override
    public Integer count() {
        return 10_000_000;
    }

    @Override
    public Integer step() {
        return 1_000_000;
    }
}
