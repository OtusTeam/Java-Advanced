package com.example.validationservice.adapter.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    @NotBlank
    private String requestId;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;

    private String surname;
    @NotBlank
    private String jobTitle;
    @NotBlank
    private String gender;
    @NotBlank
    private String mail;
    @NotNull
    @Valid
    private DocumentDto document;
    @NotBlank
    private String dateOfBirth;
}
