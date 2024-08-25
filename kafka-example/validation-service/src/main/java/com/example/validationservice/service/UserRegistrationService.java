package com.example.validationservice.service;

import com.example.validationservice.adapter.dto.request.UserDto;
import com.example.validationservice.adapter.dto.response.AccountDto;

public interface UserRegistrationService {

    void registerAccountForUser(UserDto userRequestDto);

    void handleResponse(AccountDto accountDto);
}
