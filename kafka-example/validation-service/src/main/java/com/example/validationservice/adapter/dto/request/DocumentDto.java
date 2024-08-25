package com.example.validationservice.adapter.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocumentDto {
    @NotBlank
    private String number;

    @NotBlank
    private String series;

    @NotBlank
    private String dateOfIssue;
}
