package ru.otus.observability.actuator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config.mail")
public class ConfigProperties {
    private String hostName;
    private int port;

    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
