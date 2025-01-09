package com.example.demo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Schema(description = "My DTO")
@Data
public class MyDTO {

    @Min(18) @Max(150)
    @Schema(description = "An age")
    private Integer age;

    @Email
    @Schema(
            description = "User email",
            example = "mail@example.com"
    )
    private String email;
}
