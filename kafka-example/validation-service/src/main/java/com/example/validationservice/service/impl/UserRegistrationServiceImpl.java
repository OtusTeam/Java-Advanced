package com.example.validationservice.service.impl;

import com.example.validationservice.adapter.dto.request.UserDto;
import com.example.validationservice.adapter.dto.response.AccountDto;
import com.example.validationservice.adapter.dto.response.ErrorResponseDto;
import com.example.validationservice.adapter.kafka.producer.KafkaMessageProducer;
import com.example.validationservice.service.UserRegistrationService;
import com.example.validationservice.service.UserRequestValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRequestValidationService userRequestValidationService;

    private final KafkaMessageProducer kafkaProducer;

    @Override
    public void registerAccountForUser(UserDto userRequestDto) {
        ErrorResponseDto errorMessageResponse = userRequestValidationService.validateUserRequests(userRequestDto);
        if(errorMessageResponse != null) {
            kafkaProducer.sendErrorResponse(errorMessageResponse);
            return;
        }
        kafkaProducer.sendRequestMessage(userRequestDto);
    }

    @Override
    public void handleResponse(AccountDto accountDto) {
        log.info("handleResponse with requestId = {}", accountDto.getRequestId());
        kafkaProducer.sendResponseMessage(accountDto);
    }
}
