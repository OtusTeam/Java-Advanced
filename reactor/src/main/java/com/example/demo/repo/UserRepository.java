package com.example.demo.repo;

import com.example.demo.dto.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    @Query(value = "SELECT u.name FROM app_user u")
    Flux<String> getAllEmails();
}
