package com.example.accountservice.adapter.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocumentDto {

    private String number;

    private String series;

    private String dateOfIssue;
}
