package ca.neilwhite.observabilitydemo.services;

import ca.neilwhite.observabilitydemo.models.dtos.FactDto;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@Observed
@RequiredArgsConstructor
public class FactService {

    private final RestTemplate restTemplate;

    public FactDto getRandomFact() {
        return restTemplate.getForObject(URI.create("https://uselessfacts.jsph.pl/random.json?language=en"),
                FactDto.class);
    }
}
