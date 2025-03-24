package com.github.iamsiberian.helmkubernetes.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
public class KubernetesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(KubernetesBackendApplication.class, args);
	}

	@GetMapping("/hello")
    public String helloWorld() throws UnknownHostException {

	    return "Hello from " + InetAddress.getLocalHost().getHostName();
    }
}
