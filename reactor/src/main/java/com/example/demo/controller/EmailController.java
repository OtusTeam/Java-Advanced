package com.example.demo.controller;

import com.example.demo.dto.EmailDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailController {
    final UserService service;

    @GetMapping(value = "/emails")
//    @GetMapping(value = "/emails", produces = MediaType.APPLICATION_NDJSON_VALUE)
//    @GetMapping(value = "/emails", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> getUsersEmails() {
    public Flux<EmailDto> getUsersEmails() {
        log.info("getting emails");
//        return service.getUsersEmails();
        return service.getUsersEmails().map(EmailDto::new);
    }

    @GetMapping("/sync/emails")
    public List<String> getEmailsBlocking() {
        return service.getUsersEmailsBlocking();
    }

    @GetMapping("/emails/ids")
    public Flux<Integer> getEmailProviderIds() {
        return service.getUsersEmailProviderIds();
    }

    @GetMapping("/sync/emails/ids")
    public List<Integer> getEmailProviderIdsBlocking() {
        return service.getUsersEmailProviderIds().collectList().block();
    }
}
