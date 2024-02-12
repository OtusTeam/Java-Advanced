package com.otus.firstopenlesson.services;

import org.springframework.stereotype.Service;

@Service
public class HashService {

    public String hash(String input) {
        return String.valueOf(input.hashCode());
    }
}
