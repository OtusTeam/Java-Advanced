package com.example.validationservice.service;

import com.example.validationservice.adapter.dto.request.UserDto;
import com.example.validationservice.adapter.dto.response.ErrorResponseDto;

public interface UserRequestValidationService {

    ErrorResponseDto validateUserRequests(UserDto userRequestDto);
}
