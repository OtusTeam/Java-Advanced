package com.baeldung;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.iamsiberian.helmkubernetes.frontend.KubernetesFrontendApplication;

@SpringBootTest(classes = KubernetesFrontendApplication.class)
public class SpringContextTest {

	@Test
	public void contextLoads() {
	}

}
