package com.example.validationservice.adapter.kafka.producer;

import com.example.validationservice.adapter.dto.request.UserDto;
import com.example.validationservice.adapter.dto.response.AccountDto;
import com.example.validationservice.adapter.dto.response.ErrorResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageProducer {

    public static final String REGISTRATION_USER_RESPONSE = "registration-user-response";

    public static final String USER_ACCOUNT_REQUEST = "user-account-request";

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper mapper;

    public void sendRequestMessage(UserDto userDto) {
        log.info("userDto = {}", userDto.getRequestId());
        kafkaTemplate.send(USER_ACCOUNT_REQUEST, convertToString(userDto));
    }

    public void sendResponseMessage(AccountDto accountDto) {
        log.info("Account for user with requestId = {}", accountDto.getRequestId());
        kafkaTemplate.send(REGISTRATION_USER_RESPONSE, convertToString(accountDto));
    }

    public void sendErrorResponse(ErrorResponseDto errorResponseDto) {
        log.info("Validation error for user with requestId = {}", errorResponseDto.getUserRequestId());
        kafkaTemplate.send(REGISTRATION_USER_RESPONSE, convertToString(errorResponseDto));
    }


    private String convertToString(Object dto) {
        try {
            return mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                    "An error occurred while converting JSON = {}" + e.getMessage(), e);
        }
    }
}
