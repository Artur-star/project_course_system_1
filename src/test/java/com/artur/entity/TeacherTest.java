package com.artur.entity;

import com.artur.util.HibernateUtil;
import org.assertj.core.api.ObjectAssert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class TeacherTest {
    @Test
    void testTeacher() {
        Serializable id;
        var expectedResult = buildTeacher();

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session1 = sessionFactory.openSession()) {
                //в session1 сохраняем сущность Teacher в БД
                session1.beginTransaction();
                id = session1.save(expectedResult);
                session1.getTransaction().commit();
            }
            try (var session2 = sessionFactory.openSession()) {
                //в session2 получаем из БД сущность Teacher, затем удаляем.
                //Создал две сессии, так как в hibernate используется
                // отложенная отправка данных и данные отправляются только после вызова commit
                session2.beginTransaction();

                var actualResult = session2.get(Teacher.class, id);

                assertThat(actualResult).isEqualTo(expectedResult);
                session2.delete(actualResult);
                session2.getTransaction().commit();
            }
        }
    }



    @Test
    void testStudent() {
        Serializable id;
        var expectedResult = buildStudent();

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session1 = sessionFactory.openSession()) {
                session1.beginTransaction();
                id = session1.save(expectedResult);
                session1.getTransaction().commit();
            }
            try (var session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                var actualResult = session2.get(Student.class, id);

                assertThat(actualResult).isEqualTo(expectedResult);
                session2.delete(actualResult);
                session2.getTransaction().commit();
            }
        }
    }

    @Test
    void testListCourses() {
        Serializable id;
        var expectedResult = buildListCourses();

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session1 = sessionFactory.openSession()) {
                session1.beginTransaction();
                id = session1.save(expectedResult);
                session1.getTransaction().commit();
            }
            try (var session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                var actualResult = session2.get(ListCourses.class, id);

                assertThat(actualResult).isEqualTo(expectedResult);
                session2.delete(actualResult);
                session2.getTransaction().commit();
            }
        }
    }

    @Test
    void testAboutCourse() {
        Serializable id;
        var expectedResult = buildAboutCourse();

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session1 = sessionFactory.openSession()) {
                session1.beginTransaction();
                id = session1.save(expectedResult);
                session1.getTransaction().commit();
            }
            try (var session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                var actualResult = session2.get(AboutCourse.class, id);

                assertThat(actualResult).isEqualTo(expectedResult);
                session2.delete(actualResult);
                session2.getTransaction().commit();
            }
        }
    }

    @Test
    void testArchiveRatings() {
        var expectedResult = buildArchiveRatings();

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session1 = sessionFactory.openSession()) {
                session1.beginTransaction();
                session1.save(expectedResult);
                session1.getTransaction().commit();
            }
            try (var session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                var actualResult = session2.get(ArchiveRatings.class, expectedResult.getCompositeKey());

                assertThat(actualResult).isEqualTo(expectedResult);
                session2.delete(actualResult);
                session2.getTransaction().commit();
            }
        }
    }

    private ArchiveRatings buildArchiveRatings() {
        return ArchiveRatings.builder()
                .compositeKey(CompositeKey.builder()
                        .listCoursesId(11)
                        .studentId(1L)
                        .build())
                .rating((short) 5)
                .build();
    }

    private AboutCourse buildAboutCourse() {
        return AboutCourse.builder()
                .teacherId(3)
                .name("Веб-разработчик")
                .costInRubles(80000)
                .maxStudentsNumber(5)
                .build();
    }

    private ListCourses buildListCourses() {
        return ListCourses.builder()
                .courseId(1)
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
                        .firstname("Анна")
                        .lastname("Кондратьева")
                        .patronymic("Алексеевна")
                        .email("anyta21@gmail.com")
                        .birthdate(LocalDate.of(1994, 9, 16))
                        .build())
                .profession("Веб-дизайнер")
                .build();
    }
}