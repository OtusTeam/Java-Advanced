package com.example.accountservice.service.db.impl;

import com.example.accountservice.dao.entity.AccountEntity;
import com.example.accountservice.dao.entity.UserEntity;
import com.example.accountservice.dao.repository.AccountRepository;
import com.example.accountservice.service.db.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountEntity createAccount(UserEntity user){
        AccountEntity account = AccountEntity.builder()
                .user(user)
                .nickname(createNickName(user.getMail()))
                .mail(user.getMail())
                .balance(0)
                .build();
        return accountRepository.save(account);
    }

    private String createNickName(String mail) {
        StringBuilder nickName = new StringBuilder("");
        int index = mail.indexOf("@");
        if(index != -1) {
            String result = mail.substring(0, index);
            nickName.append(result);
        }
        return nickName.toString();
    }
}
