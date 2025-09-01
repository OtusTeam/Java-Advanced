package ca.neilwhite.observabilitydemo.controllers;

import ca.neilwhite.observabilitydemo.models.dtos.FactDto;
import ca.neilwhite.observabilitydemo.services.FactService;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Observed
@RestController
@RequiredArgsConstructor
@RequestMapping("/facts")
public class FactController {

    private final FactService service;

    @GetMapping
    public FactDto getRandomFact() {
        return this.service.getRandomFact();
    }
}
