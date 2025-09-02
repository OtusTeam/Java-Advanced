package ca.neilwhite.observabilitydemo.controllers;

import ca.neilwhite.observabilitydemo.exceptions.CustomerAlreadyExistsException;
import ca.neilwhite.observabilitydemo.exceptions.CustomerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ProblemDetail handleBadRequestException(CustomerAlreadyExistsException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/400"));

        log.error("Exception: ", exception);

        return problemDetail;
    }

    @ExceptionHandler
    public ProblemDetail handleNotFoundException(CustomerNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setType(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404"));

        log.error("Exception: ", exception);

        return problemDetail;
    }
}
