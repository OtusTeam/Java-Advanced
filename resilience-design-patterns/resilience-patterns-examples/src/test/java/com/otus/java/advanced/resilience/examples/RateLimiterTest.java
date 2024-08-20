package com.otus.java.advanced.resilience.examples;

import com.otus.java.advanced.resilience.examples.clients.ClientRest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RateLimiterTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private ClientRest clientRest;

    @Test
    void testNotLimit() {
        var countRequest = 2;
        Mockito.when(clientRest.callApi()).thenReturn("");
        IntStream.rangeClosed(1, countRequest)
                .parallel()
                .forEach(it -> {
                    var response = testRestTemplate.getForEntity("/rate-limiter", String.class);
                    assertEquals(HttpStatus.OK, response.getStatusCode());
                });
        verify(clientRest, times(2)).callApi();
    }

    @Test
    void testLimit() {
        var limitForPeriod = 2;
        var countRequest = limitForPeriod + 4;
        Mockito.when(clientRest.callApi()).thenReturn("");

        var responses = new CopyOnWriteArrayList<ResponseEntity<String>>();

        IntStream.rangeClosed(1, countRequest)
                .parallel()
                .forEach(it -> responses.add(testRestTemplate.getForEntity("/rate-limiter", String.class)));

        assertEquals(countRequest, responses.size());
        assertEquals(limitForPeriod,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.OK).count());
        assertEquals(limitForPeriod,
                countRequest - responses.stream().filter(it -> it.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS).count());
        verify(clientRest, times(2)).callApi();
    }

    @Test
    void testNotRpmLimit() {
        var countRequestRps = 2;
        var countRequestRpm = 4;
        var repeat = 3;

        Mockito.when(clientRest.callApi()).thenReturn("");

        var responses = new CopyOnWriteArrayList<ResponseEntity<String>>();

        IntStream.rangeClosed(1, repeat)
                .forEach(it -> {
                            IntStream.rangeClosed(1, countRequestRps)
                                    .forEach(it1 ->
                                            responses.add(testRestTemplate.getForEntity("/rate-rpm-limiter", String.class)));
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
        assertEquals(countRequestRps * repeat, responses.size());
        assertEquals(countRequestRpm,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.OK).count());
        assertEquals(countRequestRps * repeat - countRequestRpm,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS).count());
        verify(clientRest, times(4)).callApi();
    }
}
