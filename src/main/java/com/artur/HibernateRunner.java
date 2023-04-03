package com.artur;
import com.artur.config.ApplicationConfiguration;
import com.artur.entity.AboutCourse;
import com.artur.repository.AboutCourseRepository;
import com.querydsl.core.Tuple;
import org.hibernate.Session;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HibernateRunner {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        var session = context.getBean("session", Session.class);

        var aboutCourseRepository = context.getBean("aboutCourseRepository", AboutCourseRepository.class);
        var byId = aboutCourseRepository.findById(1);
        System.out.println(byId.get());
    }
}
