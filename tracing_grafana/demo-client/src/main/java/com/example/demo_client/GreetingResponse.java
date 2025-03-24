package com.example.demo_client;

public class GreetingResponse {
    private String greeting;

    public GreetingResponse() {
    }

    public GreetingResponse(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
