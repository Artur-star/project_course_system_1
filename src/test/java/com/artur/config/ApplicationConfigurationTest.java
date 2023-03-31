package com.artur.config;

import com.artur.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.testcontainers.containers.PostgreSQLContainer;

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

    @Bean
    public Session session() {
        return (Session) Proxy.newProxyInstance(Session.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args) -> method.invoke(buildSessionFactory().openSession(), args));
    }

    @Bean
    @Scope(scopeName = BeanDefinition.SCOPE_SINGLETON)
    public SessionFactory buildSessionFactory() {
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
        configuration.addAnnotatedClass(Teacher.class);
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(AboutCourse.class);
        configuration.addAnnotatedClass(Rating.class);
        configuration.configure();

        return configuration.buildSessionFactory();
    }
}
