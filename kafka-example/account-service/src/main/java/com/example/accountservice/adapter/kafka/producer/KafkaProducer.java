package com.example.accountservice.adapter.kafka.producer;

import com.example.accountservice.adapter.dto.response.AccountDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    public static final String USER_ACCOUNT_RESPONSE = "user-account-response";

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper mapper;

    public void sendResponseMessage(AccountDto accountDto) {
        log.info("Account for user with requestId = {}", accountDto.getRequestId());
        kafkaTemplate.send(USER_ACCOUNT_RESPONSE, convertToString(accountDto));
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
