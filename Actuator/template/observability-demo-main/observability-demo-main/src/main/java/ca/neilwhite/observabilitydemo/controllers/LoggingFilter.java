package ca.neilwhite.observabilitydemo.controllers;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Instant start = Instant.now();

        filterChain.doFilter(request, response);

        if (!request.getRequestURI().contains("actuator")) {
            log.info("{} {} {} - {}ms", request.getMethod(), request.getRequestURI(), response.getStatus(),
                    Duration.between(start, Instant.now()).toMillis());
        }
    }
}
