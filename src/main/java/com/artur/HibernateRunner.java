package com.artur;
//import com.artur.config.ApplicationConfiguration;
//import com.artur.database.entity.AboutCourse;
//import com.artur.config.DatabaseProperties;
import com.artur.database.repository.AboutCourseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class HibernateRunner {
    public static void main(String[] args) {
        var context = SpringApplication.run(HibernateRunner.class, args);
    }
}
