package com.example.accountservice.adapter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String requestId;

    private String nickName;

    private UUID userId;

    private String mail;

    private String dateOfRegistration;
}