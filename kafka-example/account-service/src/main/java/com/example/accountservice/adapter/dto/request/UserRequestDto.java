package com.example.accountservice.adapter.dto.request;

import com.example.accountservice.adapter.dto.request.DocumentDto;
import lombok.Data;

@Data
public class UserRequestDto {

    private String requestId;

    private String name;

    private String lastName;

    private String surname;

    private String jobTitle;

    private String gender;

    private String mail;

    private DocumentDto document;

    private String dateOfBirth;
}
