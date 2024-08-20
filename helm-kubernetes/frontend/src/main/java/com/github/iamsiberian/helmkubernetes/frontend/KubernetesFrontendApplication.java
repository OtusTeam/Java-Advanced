package com.github.iamsiberian.helmkubernetes.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@SpringBootApplication
public class KubernetesFrontendApplication {
	private static final String RESOURCE_URL = "http://helm-kubernetes-backend:8080/hello";
	private final RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) {
		SpringApplication.run(KubernetesFrontendApplication.class, args);
	}

	@GetMapping("/hello")
	public String helloWorld() {
		ResponseEntity<String> response = restTemplate.getForEntity(RESOURCE_URL, String.class);
		return "Message from backend is: " + response.getBody();
	}
}
