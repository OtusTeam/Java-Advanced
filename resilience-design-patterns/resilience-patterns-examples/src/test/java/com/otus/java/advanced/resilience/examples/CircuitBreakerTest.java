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
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CircuitBreakerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private ClientRest clientRest;

    final List<ResponseEntity<String>> responses = new ArrayList<>();

    @Test
    void circuitBreakerCountOpenTest() {
        var numberSuccessfulFirst = 4;
        var numberNotSuccessful = 10;

        Mockito.when(clientRest.callApi())
                .thenReturn("", returnMockSuccess(numberSuccessfulFirst - 1))
                .thenThrow(returnMockNotSuccess(numberNotSuccessful));

        IntStream.rangeClosed(1, numberSuccessfulFirst + numberNotSuccessful)
                .forEach(it -> responses.add(testRestTemplate.getForEntity("/circuit-breaker", String.class)));

        verify(clientRest, times(11)).callApi();

        assertEquals(numberSuccessfulFirst + numberNotSuccessful, responses.size());
        assertEquals(numberSuccessfulFirst,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.OK).count());
        assertEquals(7,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR).count());
        assertEquals(3,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE).count());

    }

    @Test
    void circuitBreakerSlowOpenTest() {
        var numberSuccessfulFirst = 4;
        var numberIsSlow = 10;

        Mockito.when(clientRest.callApi())
                .thenReturn("", returnMockSuccess(numberSuccessfulFirst - 1))
                .thenAnswer(
                        (Answer<String>) invocation -> {
                            Thread.sleep(300);
                            return "";
                        }
                );

        IntStream.rangeClosed(1, numberSuccessfulFirst + numberIsSlow)
                .forEach(it -> responses.add(testRestTemplate.getForEntity("/circuit-breaker", String.class)));

        verify(clientRest, times(11)).callApi();

        assertEquals(numberSuccessfulFirst + numberIsSlow, responses.size());
        assertEquals(11,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.OK).count());
        assertEquals(0,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR).count());
        assertEquals(3,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE).count());

    }

    @Test
    void circuitBreakerHalfOpenToClosedTest() throws InterruptedException {

        var numberSuccessfulFirst = 4;
        var numberNotSuccessfulFirst = 10;
        var numberSuccessfulHalfOpen = 1;
        var numberSuccessfulSecond = 2;
        var numberNotSuccessfulSecond = 3;

        Mockito.when(clientRest.callApi())
                .thenReturn("", returnMockSuccess(numberSuccessfulFirst - 1))
                .thenThrow(returnMockNotSuccess(7)) //Указываем количества до open
                .thenReturn("", returnMockSuccess(numberSuccessfulHalfOpen - 1)) //в HalfOpen возвращаем успех
                .thenReturn("", returnMockSuccess(numberSuccessfulSecond - 1))
                .thenThrow(returnMockNotSuccess(numberNotSuccessfulSecond));


        IntStream.rangeClosed(1, numberSuccessfulFirst + numberNotSuccessfulFirst)
                .forEach(it -> responses.add(testRestTemplate.getForEntity("/circuit-breaker", String.class)));

        assertEquals(3,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE).count());

        Thread.sleep(1000);

        responses.clear();
        IntStream.rangeClosed(1, numberSuccessfulHalfOpen + numberSuccessfulSecond + numberNotSuccessfulSecond)
                .forEach(it -> responses.add(testRestTemplate.getForEntity("/circuit-breaker", String.class)));

        assertEquals(numberSuccessfulHalfOpen + numberSuccessfulSecond,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.OK).count());
        assertEquals(numberNotSuccessfulSecond,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR).count());
    }

    @Test
    void circuitBreakerHalfOpenToOpenTest() throws InterruptedException {

        var numberSuccessfulFirst = 4;
        var numberNotSuccessfulFirst = 10;
        var numberSuccessfulHalfOpen = 1;
        var numberSuccessfulSecond = 2;
        var numberNotSuccessfulSecond  = 3;

        Mockito.when(clientRest.callApi())
                .thenReturn("", returnMockSuccess(numberSuccessfulFirst - 1))
                .thenThrow(returnMockNotSuccess(7)) //Указываем количества до open
                .thenThrow(returnMockNotSuccess(numberSuccessfulHalfOpen)) //в HalfOpen возвращаем не успех
                .thenReturn("", returnMockSuccess(numberSuccessfulSecond - 1)) // не срабатывает
                .thenThrow(returnMockNotSuccess(numberNotSuccessfulSecond)); // не срабатывает


        IntStream.rangeClosed(1, numberSuccessfulFirst + numberNotSuccessfulFirst)
                .forEach(it -> responses.add(testRestTemplate.getForEntity("/circuit-breaker", String.class)));

        assertEquals(3,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE).count());

        Thread.sleep(1000);

        responses.clear();
        IntStream.rangeClosed(1, numberSuccessfulHalfOpen + numberSuccessfulSecond + numberNotSuccessfulSecond)
                .forEach(it -> responses.add(testRestTemplate.getForEntity("/circuit-breaker", String.class)));

        assertEquals(0,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.OK).count());
        assertEquals(numberSuccessfulHalfOpen,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR).count());
        assertEquals( numberSuccessfulSecond + numberNotSuccessfulSecond,
                responses.stream().filter(it -> it.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE).count());
    }


    private String[] returnMockSuccess(int count) {
        return IntStream.rangeClosed(1, count).boxed().map(it -> "").toArray(String[]::new);
    }

    private Throwable[] returnMockNotSuccess(int count) {
        Throwable[] result = new Throwable[count];
        IntStream.rangeClosed(1, count).forEach(it -> result[it - 1] = new IllegalStateException());
        return result;
    }
}
