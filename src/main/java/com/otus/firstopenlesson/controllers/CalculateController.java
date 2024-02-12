package com.otus.firstopenlesson.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
public class CalculateController {

    @GetMapping("/calculate")
    public ResponseEntity<String> calculate(RequestEntity<String> rq) {
        log.info("Income request: headers=" + rq.getHeaders() + ", body=" + rq.getBody());

        StringBuilder result = null;
        if (rq.getBody() != null) {
            result = new StringBuilder(rq.getBody()).reverse();
        }
        return ResponseEntity.of(Optional.of("This is Success! \n Inverted=" + result));
    }
}
