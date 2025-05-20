package com.example.demo;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Hello API",
        description = "It just saying hello!"
)
@RestController
public class TestController {
    private final DummyService service = new DummyService();

    @Operation(
            summary = "Hello message provider",
            description = "Saying hello and only that")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/user-email")
    public String getUserEmail(
            @Parameter(
                    required = true,
                    description = "ID of the user")
            @RequestParam("userId")  String userId) {
        return service.getUserEmailById(userId);
    }

    @GetMapping("/user")
    public MyDTO getUser(
            @Parameter(
                    required = true,
                    description = "ID of the user")
            @RequestParam("userId") String userId) {
        return new MyDTO();
    }

    @PostMapping("/user")
    @Operation(description = "Create user")
    public MyDTO createUser(@RequestBody MyDTO user) {
        return user;
    }

    @Hidden
    @GetMapping("/secret")
    public String secretEndpoint() {
        return "";
    }
}
