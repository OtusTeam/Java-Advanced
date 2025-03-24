package com.example.accountservice.service.impl;

import com.example.accountservice.adapter.dto.request.UserRequestDto;
import com.example.accountservice.adapter.dto.response.AccountDto;
import com.example.accountservice.adapter.kafka.producer.KafkaProducer;
import com.example.accountservice.dao.entity.AccountEntity;
import com.example.accountservice.dao.entity.UserEntity;
import com.example.accountservice.service.HandleUserRegistration;
import com.example.accountservice.service.db.AccountService;
import com.example.accountservice.service.db.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandleUserRegistrationImpl implements HandleUserRegistration {

    private final KafkaProducer kafkaProducer;
    private final UserService userService;
    private final AccountService accountService;

    @Override
    public void handleRegistrationRequest(UserRequestDto userRequestDto) {
        UserEntity user = userService.saveUser(userRequestDto);
        AccountEntity account = accountService.createAccount(user);
        AccountDto accountDto = AccountDto.builder().
                nickName(account.getNickname())
                .requestId(userRequestDto.getRequestId())
                .userId(user.getId())
                .dateOfRegistration(account.getCreatedAt().toString())
                .mail(account.getMail())
                .build();
        kafkaProducer.sendResponseMessage(accountDto);
    }
}
