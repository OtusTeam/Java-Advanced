package com.otus.java.advanced.load;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final ClientApi clientApi;

    public Controller(ClientApi clientApi) {
        this.clientApi = clientApi;
    }

    private int sleepTimeout;

    @GetMapping(value = "/request/one")
    public String getRequestOne() throws InterruptedException {
        return clientApi.sendOne(sleepTimeout);
    }

    @GetMapping(value = "/request/two")
    public String getRequestTwo() {
        return clientApi.sendTwo();
    }

    @PostMapping(value = "/degradation")
    public int reverseDegradation(@RequestBody SleepTimeout sleepTimeout) {
        this.sleepTimeout = sleepTimeout.getValue();
        return this.sleepTimeout;
    }
}
