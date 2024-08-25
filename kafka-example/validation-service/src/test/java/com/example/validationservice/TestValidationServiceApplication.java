package com.example.validationservice;

import org.springframework.boot.SpringApplication;

public class TestValidationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(ValidationServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
