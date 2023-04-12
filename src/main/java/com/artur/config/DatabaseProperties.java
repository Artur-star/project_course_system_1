package com.artur.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "db")
public record DatabaseProperties(String url,
                                 String username,
                                 String password,
                                 String driver,
                                 String hosts) {
}
