package com.artur;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class HibernateRunner {
    public static void main(String[] args) {
        var context = SpringApplication.run(HibernateRunner.class, args);
    }
}
