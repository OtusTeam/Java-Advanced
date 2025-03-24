package com.otus.java.advanced.resilience.examples;

import com.otus.java.advanced.resilience.examples.clients.ClientRest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimeLimiterTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private ClientRest clientRest;

    @Test
    void testNotLimit() {
        var timeoutDuration = 1000;
        Mockito.when(clientRest.callApi()).thenAnswer(
                (Answer<String>) invocation -> {
                    Thread.sleep(timeoutDuration / 2);
                    return "";
                }
        );
        var response = testRestTemplate.getForEntity("/time-limiter", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(clientRest, times(1)).callApi();
    }

    @Test
    void testLimit() {
        var timeoutDuration = 1000;
        Mockito.when(clientRest.callApi()).thenAnswer(
                (Answer<String>) invocation -> {
                    Thread.sleep(timeoutDuration + 500);
                    return "";
                }
        );
        var response = testRestTemplate.getForEntity("/time-limiter", String.class);
        assertEquals(HttpStatus.REQUEST_TIMEOUT, response.getStatusCode());
    }
}
