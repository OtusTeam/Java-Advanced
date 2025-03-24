package com.otus.javaadvanced.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeatureFlags {
    private Map<String, Boolean> flags = new ConcurrentHashMap<>();
}
