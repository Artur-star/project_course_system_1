package com.artur.database.repository;

import com.artur.annotation.IT;
import com.artur.config.ApplicationConfigurationTest;
import com.artur.database.entity.AboutCourse;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
//@Sql(value = {"classpath:base/init.sql", "classpath:base/data.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryAboutCourseTest {

    AnnotationConfigApplicationContext context;
//    SessionFactory sessionFactory;
    private EntityManager entityManager;
    private AboutCourseRepository aboutCourseRepository;


    @BeforeAll
    void startAll() {
        context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
//        sessionFactory = context.getBean("sessionFactory", SessionFactory.class);
        entityManager = context.getBean("entityManager", EntityManager.class);
    }

    @Test
    void findAllAboutCourseRepositoryTest() {
//        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

//        aboutCourseRepository = new AboutCourseRepository(entityManager);

        var actualList = aboutCourseRepository.findAll();

        assertThat(actualList).hasSize(4);
//        var expectedEntity = entityManager.find(AboutCourse.class, 1);
//        var expectedEntity2 = entityManager.find(AboutCourse.class, 2);
//        var expectedEntity3 = entityManager.find(AboutCourse.class, 3);
//        assertThat(actualList).hasSize(12);


//        entityManager.getTransaction().rollback();
    }

    @Test
    void findByIdAboutCourseRepositoryTest() {
//        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

//        aboutCourseRepository = new AboutCourseRepository(entityManager);

        var actualAboutCourse = aboutCourseRepository.findById(UtilSave.idAboutCourse()).orElse(null);

        assertThat(actualAboutCourse).isNotNull();


//        entityManager.getTransaction().rollback();
    }

    @Test
    void deleteAboutCourseRepositoryTest() {
//        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

//        aboutCourseRepository = new AboutCourseRepository(entityManager);
//        var aboutCourse = entityManager.find(AboutCourse.class, UtilSave.idAboutCourse());

//        aboutCourseRepository.delete(aboutCourse);

        var list = aboutCourseRepository.findAll();
        assertThat(list).hasSize(2);


//        entityManager.getTransaction().rollback();

    }

    @Test
    void saveAboutCourseRepositoryTest() {

//        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

//        aboutCourseRepository = new AboutCourseRepository(entityManager);
        var aboutCourse = UtilSave.buildAboutCourse();

        var actualEntity = aboutCourseRepository.save(aboutCourse);
//        entityManager.detach(actualEntity);

        var list = aboutCourseRepository.findAll();
//        var expectedEntity = entityManager.find(AboutCourse.class, UtilSave.idAboutCourse());
        assertThat(list).hasSize(4);
//        assertThat(list).contains(expectedEntity);

//        entityManager.getTransaction().rollback();
    }

//    @Test
//    void updateAboutCourseRepositoryTest() {

//        entityManager.getTransaction().begin();
//        UtilDelete.deleteData(sessionFactory);
//        UtilSave.importData(sessionFactory);

//        aboutCourseRepository = new AboutCourseRepository(entityManager);
//        var expectedAboutCourse = AboutCourse.builder()
//                .id(UtilSave.idAboutCourse())
//                .name("JavaScript-разработчик")
//                .costInRubles(60000)
//                .maxStudentsNumber(6)
//                .build();
//
//        aboutCourseRepository.update(expectedAboutCourse);

//        var actualAboutCourse = entityManager.find(AboutCourse.class, UtilSave.idAboutCourse());
//        assertThat(actualAboutCourse.toString()).isEqualTo(expectedAboutCourse.toString());

//        entityManager.getTransaction().rollback();
//    }
}
