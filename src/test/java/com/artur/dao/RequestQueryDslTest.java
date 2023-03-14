package com.artur.dao;

import com.artur.dto.AboutCourseFilter;
import com.artur.dto.TeacherFilter;
import com.artur.entity.AboutCourse;
import com.artur.entity.Course;
import com.artur.entity.Rating;
import com.artur.entity.Student;
import com.artur.util.HibernateUtil;
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

    SessionFactory sessionFactory;
    RequestQueryDsl queryDsl = RequestQueryDsl.getInstance();
    RequestCriteriaAPI criteriaAPI = RequestCriteriaAPI.getInstance();

    @BeforeAll
    void start() {
        sessionFactory = HibernateUtil.buildSessionFactory();
    }

    @AfterAll
    void finish() {
        sessionFactory.close();
    }

    //QueryDsl
    @Test
    void findAllStudentByCourseName() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var aboutCourse = UtilSave.buildAboutCourse();
        var teacher = UtilSave.buildTeacher();
        var course = UtilSave.buildCourse();
        var student = UtilSave.buildStudent();
        var rating = UtilSave.buildRating();
        teacher.setAboutCourse(aboutCourse);
        aboutCourse.addAboutCourse(course);
        rating.setStudent(student);
        rating.setCourse(course);
        session.save(teacher);
        session.save(aboutCourse);
        session.save(student);
        session.save(course);
        session.save(rating);

        var students = queryDsl.findAllStudentByCourseName(session, AboutCourseFilter.builder().name("Веб-разработчик").build());
        var actualResult = students.stream().map(Student::getId).collect(toList());

        assertThat(actualResult).contains(1L);
        session.getTransaction().commit();
    }

    @Test
    void findCountStudentByTeacherNameAndSurname() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var aboutCourse = UtilSave.buildAboutCourse();
        var teacher = UtilSave.buildTeacher();
        var course = UtilSave.buildCourse();
        var student = UtilSave.buildStudent();
        var rating = UtilSave.buildRating();
        teacher.setAboutCourse(aboutCourse);
        aboutCourse.addAboutCourse(course);
        rating.setStudent(student);
        rating.setCourse(course);
        session.save(teacher);
        session.save(aboutCourse);
        session.save(student);
        session.save(course);
        session.save(rating);

        var studentsCount = queryDsl.findCountStudentByTeacherNameAndSurname(session, TeacherFilter.builder().firstname("Диего").lastname("Симеоне").build());

        assertThat(studentsCount).isEqualTo(1L);
        session.getTransaction().commit();
    }

    @Test
    void findCourseNamesWithOrderedByAvgAgeStudents() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var aboutCourse = UtilSave.buildAboutCourse();
        var teacher = UtilSave.buildTeacher();
        var course = UtilSave.buildCourse();
        var student = UtilSave.buildStudent();
        var rating = UtilSave.buildRating();
        teacher.setAboutCourse(aboutCourse);
        aboutCourse.addAboutCourse(course);
        rating.setStudent(student);
        rating.setCourse(course);
        session.save(teacher);
        session.save(aboutCourse);
        session.save(student);
        session.save(course);
        session.save(rating);

        var tuples = queryDsl.findCourseNamesWithOrderedByAvgAgeStudents(session);
        var aboutCourses = tuples.stream()
                .map(it -> it.get(0, String.class)).collect(toList());

        assertThat(aboutCourses).contains("Веб-разработчик");

        var avgAge = tuples.stream()
                .map(it -> it.get(1, Double.class))
                .map(a -> LocalDate.now().getYear() - a.intValue())
                .collect(toList());

        assertThat(avgAge).contains(33);

        session.getTransaction().commit();
    }

    //CriteriaAPI
    @Test
    void findAll() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var aboutCourse = UtilSave.buildAboutCourse();
        var teacher = UtilSave.buildTeacher();
        var course = UtilSave.buildCourse();
        var student = UtilSave.buildStudent();
        var rating = UtilSave.buildRating();
        teacher.setAboutCourse(aboutCourse);
        aboutCourse.addAboutCourse(course);
        rating.setStudent(student);
        rating.setCourse(course);
        session.save(teacher);
        session.save(aboutCourse);
        session.save(student);
        session.save(course);
        session.save(rating);

        var students = criteriaAPI.findAll(session);

        var actualResult = students.stream().map(Student::getId).collect(toList());

        assertThat(actualResult).contains(1L);

        session.getTransaction().commit();
    }

    @Test
    void findMaxCountStudentByFirstnameAndLastnameTeacher() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var aboutCourse = UtilSave.buildAboutCourse();
        var teacher = UtilSave.buildTeacher();
        var course = UtilSave.buildCourse();
        var student = UtilSave.buildStudent();
        var rating = UtilSave.buildRating();
        teacher.setAboutCourse(aboutCourse);
        aboutCourse.addAboutCourse(course);
        rating.setStudent(student);
        rating.setCourse(course);
        session.save(teacher);
        session.save(aboutCourse);
        session.save(student);
        session.save(course);
        session.save(rating);

        var tuples = criteriaAPI.findMaxCountStudentByFirstnameAndLastnameTeacher(session);

        var countStudent = tuples.stream().map(it -> (Long) it[0]).collect(toList());
        assertThat(countStudent).contains(1L);

        var teacherFirstname = tuples.stream().map(it -> (String) it[1]).collect(toList());
        assertThat(teacherFirstname).contains("Диего");

        var teacherLastname = tuples.stream().map(it -> (String) it[2]).collect(toList());
        assertThat(teacherLastname).contains("Симеоне");

        session.getTransaction().commit();
    }
}
