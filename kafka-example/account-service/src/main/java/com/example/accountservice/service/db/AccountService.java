package com.example.accountservice.service.db;

import com.example.accountservice.dao.entity.AccountEntity;
import com.example.accountservice.dao.entity.UserEntity;


public interface AccountService {

    AccountEntity createAccount(UserEntity user);
}
