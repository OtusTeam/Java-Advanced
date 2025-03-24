package com.example.validationservice.adapter.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AccountDto {
    private String requestId;

    private String nickName;

    private UUID userId;

    private String mail;

    private String dateOfRegistration;
}
