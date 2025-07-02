package com.example.demo.controller;

import com.example.demo.dto.CreateUserRq;
import com.example.demo.dto.User;
import com.example.demo.dto.EmailDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    final UserService service;

    @PostMapping("/user/create")
    public Mono<User> create(@RequestBody CreateUserRq createUserRq) {
        return service.create(createUserRq.getName(), createUserRq.getEmail());
    }

    @GetMapping(value = "/users-emails")
//    @GetMapping(value = "/users-emails", produces = MediaType.APPLICATION_NDJSON_VALUE)
//    @GetMapping(value = "/users-emails", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> getUsersEmails() {
    public Flux<EmailDto> getUsersEmails() {
        log.info("getting emails");
//        return service.getUsersEmails();
        return service.getUsersEmails().map(EmailDto::new);

    }

    @GetMapping("/sync/users-emails")
    public List<String> getEmailsBlocking() {
        return service.getUsersEmailsBlocking();
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

    @GetMapping("/users-email-provider-ids")
    public Flux<Integer> getEmailProviderIds() {
        return service.getUsersEmailProviders();
    }
}
