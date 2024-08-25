package com.example.accountservice.adapter.kafka.listener;

import com.example.accountservice.adapter.dto.request.UserRequestDto;
import com.example.accountservice.service.HandleUserRegistration;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListener {

    private final ObjectMapper objectMapper;
    private final HandleUserRegistration handleUserRegistration;

    public static final String USER_ACCOUNT_REQUEST = "user-account-request";

    @org.springframework.kafka.annotation.KafkaListener(topics = USER_ACCOUNT_REQUEST)
    public void receiveMessage(@Payload String message) {
        UserRequestDto userDto;
        try {
            userDto = objectMapper.readValue(message, UserRequestDto.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }
        log.info("Received request with requestId = {}", userDto.getRequestId());
        handleUserRegistration.handleRegistrationRequest(userDto);
    }

}
