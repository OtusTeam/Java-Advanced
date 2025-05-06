package com.example.demo.service;

import com.example.demo.dto.User;
import com.example.demo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final ApplicationEventPublisher publisher;

    public Flux<User> all() {
        return repository.findAll();
    }

    public Mono<User> get(Integer id) {
        return repository.findById(id);
    }

    public Flux<String> getUsersEmails() {
        return repository.getAllEmails();
    }

    public List<String> getUsersEmailsBlocking() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return List.of("alex@mail.ru", "anastasia@mail.ru", "gleb@mail.ru");
    }

    public Mono<User> update(Integer id, String name, String email) {
        return repository.findById(id)
                .map(u -> new User(id, name, email))
                .flatMap(repository::save);
    }

    public Mono<User> delete(Integer id) {
        return repository.findById(id)
                .flatMap(u -> repository.delete(u).thenReturn(u));
    }

    public Mono<User> create(String name, String email) {
        return repository.save(new User(null, name, email));
    }
}
