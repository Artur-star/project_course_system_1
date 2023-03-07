package com.artur.entity;

import com.artur.util.HibernateUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;


class CourseSystemTest {
    @Test
    void testSaveAndGetTeacher() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            var aboutCourse = buildAboutCourse();
            var expectedTeacher = buildTeacher();
            expectedTeacher.setAboutCourse(aboutCourse);
            session.save(aboutCourse);

            var idTeacher = session.save(expectedTeacher);
            session.evict(expectedTeacher);
            var actualTeacher = session.get(Teacher.class, idTeacher);

            assertThat(actualTeacher.getId()).isEqualTo(expectedTeacher.getId());
            session.getTransaction().commit();
        }
    }

    @Test
    void testSaveAndGetAboutCourse() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            var expectedAboutCourse = buildAboutCourse();
            var teacher = buildTeacher();
            teacher.setAboutCourse(expectedAboutCourse);
            session.save(teacher);

            var idAboutCourse = session.save(expectedAboutCourse);
            session.evict(expectedAboutCourse);
            var actualAboutCourse = session.get(AboutCourse.class, idAboutCourse);

            assertThat(actualAboutCourse.getId()).isEqualTo(expectedAboutCourse.getId());
            session.getTransaction().commit();
        }
    }

    @Test
    void testSaveAndGetArchiveRatings() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session = sessionFactory.openSession()) {
                session.beginTransaction();
                var aboutCourse = buildAboutCourse();
                var teacher = buildTeacher();
                var course = buildCourse();
                var student = buildStudent();
                var expectedArchiveRatings = buildArchiveRatings();
                teacher.setAboutCourse(aboutCourse);
                aboutCourse.addAboutCourse(course);
                expectedArchiveRatings.setStudent(student);
                expectedArchiveRatings.setCourse(course);
                session.save(teacher);
                session.save(aboutCourse);
                session.save(student);
                session.save(course);

                var idArchiveRatings = session.save(expectedArchiveRatings);
                session.evict(expectedArchiveRatings);
                var actualArchiveRatings = session.get(ArchiveRatings.class, idArchiveRatings);

                assertThat(actualArchiveRatings).isEqualTo(expectedArchiveRatings);
                session.getTransaction().commit();
            }
        }
    }

    @Test
    void testSaveAndGetStudent() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session = sessionFactory.openSession()) {
                session.beginTransaction();
                var expectedStudent = buildStudent();

                var studentId = session.save(expectedStudent);
                session.evict(expectedStudent);
                var actualStudent = session.get(Student.class, studentId);

                assertThat(actualStudent.getId()).isEqualTo(expectedStudent.getId());
                session.getTransaction().commit();
            }
        }
    }

    @Test
    void testSaveAndGetCourse() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session = sessionFactory.openSession()) {
                session.beginTransaction();
                var expectedCourse = buildCourse();
                var aboutCourse = buildAboutCourse();
                var teacher = buildTeacher();
                teacher.setAboutCourse(aboutCourse);
                aboutCourse.addAboutCourse(expectedCourse);
                session.save(teacher);
                session.save(aboutCourse);

                var courseId = session.save(expectedCourse);
                session.evict(expectedCourse);
                var actualCourse = session.get(Course.class, courseId);

                assertThat(actualCourse.getId()).isEqualTo(expectedCourse.getId());
                session.getTransaction().commit();
            }
        }
    }

    private ArchiveRatings buildArchiveRatings() {
        return ArchiveRatings.builder()
                .rating((short) 5)
                .build();
    }

    private AboutCourse buildAboutCourse() {
        return AboutCourse.builder()
                .name("Веб-разработчик")
                .costInRubles(80000)
                .maxStudentsNumber(5)
                .build();
    }

    private Course buildCourse() {
        return Course.builder()
                .start(LocalDate.of(2022, 10, 10))
                .finish(LocalDate.of(2023, 10, 10))
                .build();
    }

    private Student buildStudent() {
        return Student.builder()
                .personalInfo(PersonalInfo.builder()
                        .firstname("Антуан")
                        .lastname("Гризман")
                        .patronymic("Артурович")
                        .email("antuan@gmail.com")
                        .birthdate(LocalDate.of(1990, 9, 16))
                        .build())
                .build();
    }

    private Teacher buildTeacher() {
        return Teacher.builder()
                .personalInfo(PersonalInfo.builder()
                        .firstname("Диего")
                        .lastname("Симеоне")
                        .patronymic("Гонсалес")
                        .email("YEFA@gmail.com")
                        .birthdate(LocalDate.of(1975, 9, 16))
                        .build())
                .profession("Веб-дизайнер")
                .build();
    }
}