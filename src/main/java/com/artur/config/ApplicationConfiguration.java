//package com.artur.config;
//
//import com.artur.database.entity.*;
//import com.artur.database.repository.*;
//import lombok.Cleanup;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
//import org.hibernate.cfg.Environment;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.context.annotation.*;
//import org.springframework.stereotype.Component;
//
//import javax.persistence.EntityManager;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.util.Properties;
//
//@Configuration
//@PropertySource("classpath:application.properties")
//@ComponentScan(basePackages = "com.artur.database.repository")
//public class ApplicationConfiguration {
//
//    @Value("${connection.url}")
//    private String connectionUrl;
//
//    @Value("${connection.driver_class}")
//    private String connectionDriverClass;
//
//    @Value("${connection.username}")
//    private String connectionUsername;
//
//    @Value("${connection.password}")
//    private String connectionPassword;
//
//    @Value("${hibernate.dialect}")
//    private String hibernateDialect;
//
//    @Value("${show_sql}")
//    private String showSql;
//
//    @Value("${format_sql}")
//    private String formatSql;
//
//    @Value("${hibernate.current_session_context_class}")
//    private String thread;
//
//    @Bean
//    public EntityManager entityManager() {
//        return (EntityManager) Proxy.newProxyInstance(EntityManager.class.getClassLoader(), new Class[]{EntityManager.class},
//                (proxy, method, args) -> method.invoke(buildSessionFactory().getCurrentSession(), args));
//    }
//
//    @Bean
//    public SessionFactory buildSessionFactory() {
//        var configuration = new org.hibernate.cfg.Configuration();
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
//        configuration.setProperty(Environment.URL, connectionUrl);
//        configuration.setProperty(Environment.DRIVER, connectionDriverClass);
//        configuration.setProperty(Environment.USER, connectionUsername);
//        configuration.setProperty(Environment.PASS, connectionPassword);
//        configuration.setProperty(Environment.DIALECT, hibernateDialect);
//        configuration.setProperty(Environment.SHOW_SQL, showSql);
//        configuration.setProperty(Environment.FORMAT_SQL, formatSql);
//        configuration.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, thread);
//        configuration.addAnnotatedClass(Teacher.class);
//        configuration.addAnnotatedClass(Student.class);
//        configuration.addAnnotatedClass(Course.class);
//        configuration.addAnnotatedClass(AboutCourse.class);
//        configuration.addAnnotatedClass(Rating.class);
//        configuration.configure();
//
//        return configuration.buildSessionFactory();
//    }
//}
