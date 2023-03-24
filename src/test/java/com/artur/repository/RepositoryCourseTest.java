package com.artur.repository;

import com.artur.entity.Course;
import com.artur.util.HibernateUtil;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryCourseTest {
    SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();;
    CourseRepository courseRepository;

    @BeforeEach
    void startEach() {
        UtilSave.importData(sessionFactory);
    }

    @AfterEach
    void endEach() {
        UtilDelete.deleteData(sessionFactory);
    }

    @Test
    void findAllAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        courseRepository = new CourseRepository(session);

        var actualList = courseRepository.findAll();

        assertThat(actualList).hasSize(4);
        var expectedEntity1 = session.get(Course.class, 1L);
        var expectedEntity2 = session.get(Course.class, 2L);
        var expectedEntity3 = session.get(Course.class, 3L);
        var expectedEntity4 = session.get(Course.class, 4L);
        assertThat(actualList).contains(expectedEntity1, expectedEntity2, expectedEntity3, expectedEntity4);

        session.getTransaction().commit();
    }

    @Test
    void findByIdAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        courseRepository = new CourseRepository(session);

        var actualAboutCourse = courseRepository.findById(UtilSave.idCourse()).orElse(null);

        assertThat(actualAboutCourse).isNotNull();

        session.getTransaction().commit();
    }

    @Test
    void deleteAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        courseRepository = new CourseRepository(session);
        var course = session.get(Course.class, UtilSave.idCourse());

        courseRepository.delete(course);

        var list = courseRepository.findAll();
        assertThat(list).hasSize(3);

        session.getTransaction().commit();
    }

    @Test
    void saveAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        courseRepository = new CourseRepository(session);
        var course = UtilSave.buildCourse();

        var actualEntity = courseRepository.save(course);
        session.evict(actualEntity);

        var list = courseRepository.findAll();
        var expectedEntity = session.get(Course.class, UtilSave.idCourse());
        assertThat(list).hasSize(5);
        assertThat(list).contains(expectedEntity);

        session.getTransaction().commit();
    }

    @Test
    void updateAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        courseRepository = new CourseRepository(session);
        var expectedCourse = Course.builder()
                .id(UtilSave.idCourse())
                .start(LocalDate.of(2020, 10, 1))
                .finish(LocalDate.of(2021, 10, 1))
                .build();

        courseRepository.update(expectedCourse);

        var actualCourse = session.get(Course.class, UtilSave.idCourse());
        assertThat(actualCourse.toString()).isEqualTo(expectedCourse.toString());

        session.getTransaction().commit();
    }
}
