package com.artur.database.repository;

import com.artur.config.ApplicationConfigurationTest;
import com.artur.database.entity.Course;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryCourseTest {
    AnnotationConfigApplicationContext context;
    SessionFactory sessionFactory;
    EntityManager entityManager;
    CourseRepository courseRepository;


    @BeforeAll
    void startAll() {
        context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
        sessionFactory = context.getBean("sessionFactory", SessionFactory.class);
        entityManager = context.getBean("entityManager", EntityManager.class);
    }

    @Test
    void findAllCourseRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

        courseRepository = new CourseRepository(entityManager);

        var actualList = courseRepository.findAll();

        assertThat(actualList).hasSize(4);
        var expectedEntity1 = entityManager.find(Course.class, 1L);
        var expectedEntity2 = entityManager.find(Course.class, 2L);
        var expectedEntity3 = entityManager.find(Course.class, 3L);
        var expectedEntity4 = entityManager.find(Course.class, 4L);
        assertThat(actualList).contains(expectedEntity1, expectedEntity2, expectedEntity3, expectedEntity4);

        entityManager.getTransaction().commit();
    }

    @Test
    void findByIdCourseRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

        courseRepository = new CourseRepository(entityManager);

        var actualAboutCourse = courseRepository.findById(UtilSave.idCourse()).orElse(null);

        assertThat(actualAboutCourse).isNotNull();

        entityManager.getTransaction().commit();
    }

    @Test
    void deleteCourseRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

        courseRepository = new CourseRepository(entityManager);
        var course = entityManager.find(Course.class, UtilSave.idCourse());

        courseRepository.delete(course);

        var list = courseRepository.findAll();
        assertThat(list).hasSize(3);

        entityManager.getTransaction().commit();
    }

    @Test
    void saveCourseRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

        courseRepository = new CourseRepository(entityManager);
        var course = UtilSave.buildCourse();

        var actualEntity = courseRepository.save(course);
        entityManager.detach(actualEntity);

        var list = courseRepository.findAll();
        var expectedEntity = entityManager.find(Course.class, UtilSave.idCourse());
        assertThat(list).hasSize(5);
        assertThat(list).contains(expectedEntity);

        entityManager.getTransaction().commit();
    }

    @Test
    void updateCourseRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

        courseRepository = new CourseRepository(entityManager);
        var expectedCourse = Course.builder()
                .id(UtilSave.idCourse())
                .start(LocalDate.of(2020, 10, 1))
                .finish(LocalDate.of(2021, 10, 1))
                .build();

        courseRepository.update(expectedCourse);

        var actualCourse = entityManager.find(Course.class, UtilSave.idCourse());
        assertThat(actualCourse.toString()).isEqualTo(expectedCourse.toString());

        entityManager.getTransaction().commit();
    }
}
