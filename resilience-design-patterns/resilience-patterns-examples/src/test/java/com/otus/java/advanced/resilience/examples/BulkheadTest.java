package com.otus.java.advanced.resilience.examples;

import com.otus.java.advanced.resilience.examples.clients.ClientRest;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BulkheadTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private ClientRest clientRest;

    @RepeatedTest(10)
    void test() {
        var repeat = 10;
        Mockito.when(clientRest.callApi()).thenAnswer(
                (Answer<String>) invocation -> {
                    Thread.sleep(300);
                    return "";
                }
        );

        var responses = new CopyOnWriteArrayList<ResponseEntity<String>>();

        IntStream.rangeClosed(1, repeat)
                .parallel()
                .forEach(it -> responses.add(testRestTemplate.getForEntity("/bulkhead", String.class)));

        assertEquals(repeat, responses.size());
        assertEquals(repeat, responses.stream().filter(it ->
                it.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS || it.getStatusCode() == HttpStatus.OK
        ).count());
        assertEquals(3, responses.stream().filter(it -> it.getStatusCode() == HttpStatus.OK).count());
        assertEquals(7, responses.stream().filter(it -> it.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS).count());
    }
}
