package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailProviderDto {
    public Integer id;
    public String fqdn;
}
