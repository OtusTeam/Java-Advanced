package com.example.demo.controller;

import com.example.demo.dto.CreateUserRq;
import com.example.demo.dto.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UserController {
    final UserService service;

    @PostMapping("/user/create")
    public Mono<User> create(@RequestBody CreateUserRq createUserRq) {
        return service.create(createUserRq.getName(), createUserRq.getEmail());
    }

    @GetMapping("/users-emails")
    public Flux<String> getUsersEmails() {
        return service.getUsersEmails();
    }

    @GetMapping("/user/{id}")
    public Mono<User> getById(@PathVariable Integer id) {
        return service.get(id);
    }

    @GetMapping("/users")
    public Flux<User> getAll() {
        return service.all();
    }
}
