package com.example.demo_server;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
public class TheHandler implements ObservationHandler<Observation.Context> {
    private static final Logger log = LoggerFactory.getLogger(TheHandler.class);

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }

    private String getKey(Observation.Context context) {
        return StreamSupport.stream(context.getLowCardinalityKeyValues().spliterator(), false)
                .map(KeyValue::getKey)
                .filter("K"::equals)
                .findFirst()
                .orElse("UNKNOWN");
    }
}
