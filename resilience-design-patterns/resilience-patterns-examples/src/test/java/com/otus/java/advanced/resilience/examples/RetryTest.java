package com.otus.java.advanced.resilience.examples;

import com.otus.java.advanced.resilience.examples.clients.ClientRest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RetryTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private ClientRest clientRest;


    @Test
    void testOk() {
        Mockito.when(clientRest.callApi())
                .thenThrow(IllegalStateException.class)
                .thenThrow(IllegalStateException.class)
                .thenReturn("");
        var response = testRestTemplate.getForEntity("/retry", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(clientRest, times(3)).callApi();
        verify(clientRest, times(0)).fallback();
    }

    @Test
    void testError() {
        Mockito.when(clientRest.callApi())
                .thenThrow(IllegalStateException.class)
                .thenThrow(IllegalStateException.class)
                .thenThrow(IllegalStateException.class)
                .thenReturn("");
        Mockito.when(clientRest.fallback()).thenThrow(IllegalStateException.class);

        var response = testRestTemplate.getForEntity("/retry", String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(clientRest, times(3)).callApi();
        verify(clientRest, times(1)).fallback();
    }

    @Test
    void testErrorFallbackOk() {
        Mockito.when(clientRest.callApi())
                .thenThrow(IllegalStateException.class)
                .thenThrow(IllegalStateException.class)
                .thenThrow(IllegalStateException.class)
                .thenReturn("");

        var response = testRestTemplate.getForEntity("/retry", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(clientRest, times(3)).callApi();
        verify(clientRest, times(1)).fallback();
    }
}
