package com.artur.repository;

import com.artur.entity.AboutCourse;
import com.artur.util.HibernateUtil;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryAboutCourseTest {
    SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();;
    AboutCourseRepository aboutCourseRepository;

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

        aboutCourseRepository = new AboutCourseRepository(session);

        var actualList = aboutCourseRepository.findAll();

        assertThat(actualList).hasSize(3);
        var expectedEntity1 = session.get(AboutCourse.class, 1);
        var expectedEntity2 = session.get(AboutCourse.class, 2);
        var expectedEntity3 = session.get(AboutCourse.class, 3);
        assertThat(actualList).contains(expectedEntity1, expectedEntity2, expectedEntity3);

        session.getTransaction().commit();
    }

    @Test
    void findByIdAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        aboutCourseRepository = new AboutCourseRepository(session);

        var actualAboutCourse = aboutCourseRepository.findById(UtilSave.idAboutCourse()).orElse(null);

        assertThat(actualAboutCourse).isNotNull();

        session.getTransaction().commit();
    }

    @Test
    void deleteAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        aboutCourseRepository = new AboutCourseRepository(session);
        var aboutCourse = session.get(AboutCourse.class, UtilSave.idAboutCourse());

        aboutCourseRepository.delete(aboutCourse);

        var list = aboutCourseRepository.findAll();
        assertThat(list).hasSize(2);

        session.getTransaction().commit();
    }

    @Test
    void saveAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        aboutCourseRepository = new AboutCourseRepository(session);
        var aboutCourse = UtilSave.buildAboutCourse();

        var actualEntity = aboutCourseRepository.save(aboutCourse);
        session.evict(actualEntity);

        var list = aboutCourseRepository.findAll();
        var expectedEntity = session.get(AboutCourse.class, UtilSave.idAboutCourse());
        assertThat(list).hasSize(4);
        assertThat(list).contains(expectedEntity);

        session.getTransaction().commit();
    }

    @Test
    void updateAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        aboutCourseRepository = new AboutCourseRepository(session);
        var expectedAboutCourse = AboutCourse.builder()
                .id(UtilSave.idAboutCourse())
                .name("JavaScript-разработчик")
                .costInRubles(60000)
                .maxStudentsNumber(6)
                .build();

        aboutCourseRepository.update(expectedAboutCourse);

        var actualAboutCourse = session.get(AboutCourse.class, UtilSave.idAboutCourse());
        assertThat(actualAboutCourse.toString()).isEqualTo(expectedAboutCourse.toString());

        session.getTransaction().commit();
    }
}
