package com.otus.javaadvanced.controllers.actuator;

import com.otus.javaadvanced.config.FeatureFlags;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

/*
    Кастомный кастомный endpoint для включения или отключения фич
    Используется в качестве примера
 */
@Component
@Endpoint(id = "features")
@AllArgsConstructor
public class FeaturesActuatorController {

    private final FeatureFlags features;

    @ReadOperation
    public FeatureFlags features() {
        return features;
    }

    @ReadOperation
    public Boolean feature(@Selector String name) {
        return features.getFlags().get(name);
    }

    @WriteOperation
    public void configureFeature(@Selector String name, Boolean enabled) {
        features.getFlags().put(name, enabled);
    }

    @DeleteOperation
    public void deleteFeature(@Selector String name) {
        features.getFlags().remove(name);
    }
}