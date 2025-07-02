package com.example.demo.debug;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailProviderDto {
    public Integer id;
    public String fqdn;
}
