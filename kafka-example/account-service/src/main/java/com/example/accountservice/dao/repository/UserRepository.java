package com.example.accountservice.dao.repository;

import com.example.accountservice.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByMail(String mail);
}
