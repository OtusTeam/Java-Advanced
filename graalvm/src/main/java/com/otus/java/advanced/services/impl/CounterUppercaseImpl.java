package com.otus.java.advanced.services.impl;

import com.otus.java.advanced.services.Counter;
import com.otus.java.advanced.services.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CounterUppercaseImpl implements Counter {

    private final Filter filter;

    @Override
    public Long count(String sentence) {

        long total = 0, last = System.currentTimeMillis();

        for (int i = 1; i < filter.count(); i++) {
            total += sentence
                    .chars()
                    .filter(filter::is)
                    .count();
            if (i % filter.step() == 0) {
                long now = System.currentTimeMillis();
                log.info("{} ({} ms)", i / 1_000_000, now - last);
                last = now;
            }
        }
        return total;
    }
}
