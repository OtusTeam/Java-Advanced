package com.otus.javaadvanced.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
@AllArgsConstructor
public class HashService {

    private static final int MAX_HASH_COUNT = 200;

    private final EncodeService encodeService;

    public String hash(String input) {
        String result = input;
        for (int i = 0; i < MAX_HASH_COUNT; i++) {
            result = hashIteration(result);
        }

        return result;
    }

    // https://www.baeldung.com/java-password-hashing
    public String hashIteration(String input) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(salt);

        byte[] hashedPassword = md.digest(input.getBytes(StandardCharsets.UTF_8));

        byte[] encode = encodeService.encode(hashedPassword);
        return new String(encode);
    }
}
