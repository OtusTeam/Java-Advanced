package com.baeldung;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.iamsiberian.helmkubernetes.backend.KubernetesBackendApplication;

@SpringBootTest(classes = KubernetesBackendApplication.class)
public class SpringContextTest {

	@Test
	public void contextLoads() {
	}

}
