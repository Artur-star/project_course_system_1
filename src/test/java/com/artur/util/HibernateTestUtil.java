package com.artur.util;

import com.artur.database.entity.*;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateTestUtil {

        public static SessionFactory buildSessionFactory() {
            var configuration = new Configuration();
            configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
            configuration.addAnnotatedClass(Teacher.class);
            configuration.addAnnotatedClass(Student.class);
            configuration.addAnnotatedClass(Course.class);
            configuration.addAnnotatedClass(AboutCourse.class);
            configuration.addAnnotatedClass(Rating.class);
            configuration.configure();

            return configuration.buildSessionFactory();
        }

}
