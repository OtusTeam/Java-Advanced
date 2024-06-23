package com.otus.javaadvanced.services;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Base64;

@RequiredArgsConstructor
public class EncodeService {

    private final Encoder encoder;

    // for emulation of large stacks
    private static final int STACK_DEPTH = 0;

    public byte[] encode(byte[] hash) {
        byte[] safe = Arrays.copyOf(hash, hash.length);
        return encodeInner(safe, 0);
    }

    private byte[] encodeInner(byte[] hash, int level) {
        if (level < STACK_DEPTH) {
            // imitation of long stacktrace
            return encodeInner(hash, ++level);
        }

        byte[] result;
        try {
            result = encoder.encode(hash);

        } catch (Exception e) {
            long count = ExceptionUtils.countException(e);
            ExceptionUtils.acceptCount(count);

            result = Base64.getEncoder().encode(hash);
        }

        return result;
    }
}
