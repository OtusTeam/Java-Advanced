package com.example.demo.controller;

import com.example.demo.dto.CreateUserRq;
import com.example.demo.dto.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    final UserService service;

    @PostMapping("/user")
    public Mono<User> create(@RequestBody CreateUserRq createUserRq) {
        return service.create(createUserRq.getName(), createUserRq.getEmail());
    }

    @GetMapping("/user/{id}")
    public Mono<User> getById(@PathVariable Integer id) {
        return service.get(id);
    }

    @GetMapping("/users")
//    @GetMapping(value = "/users", produces = MediaType.APPLICATION_NDJSON_VALUE)
//    @GetMapping(value = "/users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> getAll() {
        return service.all();
    }

    @PutMapping("/user/{id}")
    public Mono<User> update(@PathVariable Integer id, @RequestBody CreateUserRq rq) {
        return service.update(id, rq.getName(), rq.getEmail());
    }

    @DeleteMapping("/user/{id}")
    public Mono<User> delete(@PathVariable Integer id) {
        return service.delete(id);
    }
}
