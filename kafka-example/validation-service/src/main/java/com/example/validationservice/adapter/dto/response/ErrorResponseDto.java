package com.example.validationservice.adapter.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponseDto {
    private String userRequestId;
    private List<String> errorFields;
    private String message;
}
