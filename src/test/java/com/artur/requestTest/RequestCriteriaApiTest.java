package com.artur.requestTest;

import com.artur.config.ApplicationConfigurationTest;
import com.artur.entity.Student;
import com.artur.request.RequestCriteriaAPI;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class RequestCriteriaApiTest {

    SessionFactory sessionFactory;
    RequestCriteriaAPI criteriaAPI = RequestCriteriaAPI.getInstance();
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);

    Session session;


    @BeforeAll
    void start() {
        sessionFactory = context.getBean(SessionFactory.class);

    }

    @BeforeEach
    void startEach() {

        UtilSave.importData(sessionFactory);
    }

    @AfterEach
    void endEach() {
        UtilDelete.deleteData(sessionFactory);
        session.close();
    }

    @AfterAll
    void endAll() {
        sessionFactory.close();
    }

    @Test
    void findAll() {
        session = context.getBean(Session.class);
        session.beginTransaction();

        var students = criteriaAPI.findAll(session);

        var actualResult = students.stream().map(Student::getId).collect(toList());

        assertThat(actualResult).contains(1L);

        session.getTransaction().commit();
    }

    @Test
    void findMaxCountStudentByFirstnameAndLastnameTeacher() {
        session.beginTransaction();

        var tuples = criteriaAPI.findMaxCountStudentByFirstnameAndLastnameTeacher(session, "Gleb", "Matveenka");

        var countStudent = tuples.stream().map(it -> it.get(0, Long.class)).collect(toList());
        assertThat(countStudent).contains(8L);

        var teacherFirstname = tuples.stream().map(it -> it.get(1, String.class)).collect(toList());
        assertThat(teacherFirstname).contains("Gleb");

        var teacherLastname = tuples.stream().map(it -> it.get(2, String.class)).collect(toList());
        assertThat(teacherLastname).contains("Matveenka");

        session.getTransaction().commit();

    }
}
