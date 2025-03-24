package com.example.accountservice.service;

import com.example.accountservice.adapter.dto.request.UserRequestDto;

public interface HandleUserRegistration {

    void handleRegistrationRequest(UserRequestDto userRequestDto);
}
