package com.otus.java.advanced.controllers;

import com.otus.java.advanced.services.Counter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "count", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CounterController {

    private final Counter counter;

    @PostMapping("/uppercase")
    public ResponseEntity<ArrayList<String>> countUppercase(RequestEntity<List<String>> request,
                                                            @RequestParam("iterations") Integer iterations) {

        var sentence = String.join(" ", Objects.requireNonNull(request.getBody()));

        var results = new ArrayList<String>();

        for (int iter = 0; iter < iterations; iter++) {
            if (iterations != 1) {
                log.info("-- iteration {} --", (iter + 1));
            }
            long start = System.currentTimeMillis();
            long total = counter.count(sentence);
            var result = String.format("total: %d (%d ms) ", total, System.currentTimeMillis() - start);
            results.add(result);
            log.info(result);
        }

        return ResponseEntity.ok(results);
    }
}
