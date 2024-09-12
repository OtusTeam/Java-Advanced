package com.otus.javaadvanced.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/*
    Дополнительная информация о состоянии приложения в /actuator/info
 */
@Component
@RequiredArgsConstructor
public class UsersInfo implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        var usersInfo = new HashMap<String, Object>();
        usersInfo.put("active", "1"); // Значение можно брать из приложения
        usersInfo.put("name", "admin");
        builder.withDetail("users", usersInfo);
    }
}