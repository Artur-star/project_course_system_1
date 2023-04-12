package com.artur.config;

import com.artur.database.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.persistence.EntityManager;
import java.lang.reflect.Proxy;

@Configuration
@PropertySource("classpath:applicationTest.properties")
@ComponentScan(basePackages = "com.artur")
public class ApplicationConfigurationTest {

    @Value("${connection.url}")
    private String connectionUrl;

    @Value("${connection.username}")
    private String connectionUsername;

    @Value("${connection.password}")
    private String connectionPassword;

    @Value("${connection.driver_class}")
    private String connectionDriverClass;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${show_sql}")
    private String showSql;

    @Value("${format_sql}")
    private String formatSql;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddlAuto;

    @Value("${hibernate.current_session_context_class}")
    private String thread;

    @Bean
    public EntityManager entityManager(SessionFactory sessionFactory) {
        return (EntityManager) Proxy.newProxyInstance(EntityManager.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args) -> method.invoke(sessionFactory.getCurrentSession(), args));
    }

    @Bean
    public SessionFactory sessionFactory() {
        var configuration = new org.hibernate.cfg.Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.setProperty(Environment.URL, connectionUrl);
        configuration.setProperty(Environment.USER, connectionUsername);
        configuration.setProperty(Environment.PASS, connectionPassword);
        configuration.setProperty(Environment.DRIVER, connectionDriverClass);
        configuration.setProperty(Environment.DIALECT, hibernateDialect);
        configuration.setProperty(Environment.SHOW_SQL, showSql);
        configuration.setProperty(Environment.FORMAT_SQL, formatSql);
        configuration.setProperty(Environment.HBM2DDL_AUTO, hibernateHbm2ddlAuto);
        configuration.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, thread);
        configuration.addAnnotatedClass(Teacher.class);
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(AboutCourse.class);
        configuration.addAnnotatedClass(Rating.class);

        return configuration.buildSessionFactory();
    }
}
