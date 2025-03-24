package com.example.validationservice.adapter.kafka.listener;

import com.example.validationservice.adapter.dto.request.UserDto;
import com.example.validationservice.adapter.dto.response.AccountDto;
import com.example.validationservice.service.UserRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {
    private final ObjectMapper objectMapper;
    private final UserRegistrationService userRegistrationService;

    public static final String REGISTRATION_USER_REQUEST = "registration-user-request";

    public static final String USER_ACCOUNT_RESPONSE = "user-account-response";

    @KafkaListener(topics = REGISTRATION_USER_REQUEST)
    public void receiveMessage(@Payload String message) {
        UserDto userDto;
        try {
            userDto = objectMapper.readValue(message, UserDto.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }
        log.info("Received request with requestId = {}", userDto.getRequestId());
        userRegistrationService.registerAccountForUser(userDto);
    }

    @KafkaListener(topics = USER_ACCOUNT_RESPONSE)
    public void receiveResponse(@Payload String message) {
        AccountDto accountDto;
        try {
            accountDto = objectMapper.readValue(message, AccountDto.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }
        log.info("Received response account with requestId = {}", accountDto);
        userRegistrationService.handleResponse(accountDto);
    }
}
