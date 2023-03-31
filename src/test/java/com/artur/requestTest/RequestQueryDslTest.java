package com.artur.requestTest;

import com.artur.config.ApplicationConfigurationTest;
import com.artur.dto.TeacherFilter;
import com.artur.entity.Student;
import com.artur.request.RequestQueryDsl;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;

@TestInstance(PER_CLASS)
public class RequestQueryDslTest {

    SessionFactory sessionFactory = new ApplicationConfigurationTest().buildSessionFactory();
    RequestQueryDsl queryDsl = RequestQueryDsl.getInstance();

    @BeforeEach
    void startEach() {
        UtilSave.importData(sessionFactory);
    }

    @AfterEach
    void endEach() {
        UtilDelete.deleteData(sessionFactory);
    }

    @Test
    void findAllStudentByCourseName() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var aboutCourse = UtilSave.getAboutCourses().get(0);

        var students = queryDsl.findAllStudentByCourseName(session, aboutCourse);
        var actualResult = students.stream().map(Student::getId).collect(toList());

        assertThat(actualResult).contains(UtilSave.idStudent());
        session.getTransaction().commit();
    }

    @Test
    void findCountStudentByTeacherNameAndSurname() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var studentsCount = queryDsl.findCountStudentByTeacherNameAndSurname(session, TeacherFilter.builder().firstname("Gleb").lastname("Matveenka").build());

        assertThat(studentsCount).isEqualTo(8L);
        session.getTransaction().commit();
    }

    @Test
    void findCourseNamesWithOrderedByAvgAgeStudents() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var tuples = queryDsl.findCourseNamesWithOrderedByAvgAgeStudents(session);
        var aboutCourses = tuples.stream()
                .map(it -> it.get(0, String.class)).collect(toList());

        assertThat(aboutCourses).contains("Web-developer", "QA-developer", "Frontend-developer on Python");

        var avgAge = tuples.stream()
                .map(it -> it.get(1, Double.class))
                .map(a -> LocalDate.now().getYear() - a.intValue())
                .collect(toList());

        assertThat(avgAge).contains(37, 34, 26);

        session.getTransaction().commit();
    }
}
