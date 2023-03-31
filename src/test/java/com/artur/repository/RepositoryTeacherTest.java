package com.artur.repository;

import com.artur.config.ApplicationConfigurationTest;
import com.artur.entity.PersonalInfo;
import com.artur.entity.Teacher;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryTeacherTest {
    SessionFactory sessionFactory = new ApplicationConfigurationTest().buildSessionFactory();;
    TeacherRepository teacherRepository;

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

        teacherRepository = new TeacherRepository(session);

        var actualList = teacherRepository.findAll();

        assertThat(actualList).hasSize(3);
        var expectedEntity1 = session.get(Teacher.class, 1);
        var expectedEntity2 = session.get(Teacher.class, 2);
        var expectedEntity3 = session.get(Teacher.class, 3);
        assertThat(actualList).contains(expectedEntity1, expectedEntity2, expectedEntity3);

        session.getTransaction().commit();
    }

    @Test
    void findByIdAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        teacherRepository = new TeacherRepository(session);

        var actualTeacher = teacherRepository.findById(UtilSave.idTeacher()).orElse(null);

        assertThat(actualTeacher).isNotNull();

        session.getTransaction().commit();
    }

    @Test
    void deleteAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        teacherRepository = new TeacherRepository(session);
        var aboutCourse = session.get(Teacher.class, UtilSave.idTeacher());

        teacherRepository.delete(aboutCourse);

        var list = teacherRepository.findAll();
        assertThat(list).hasSize(2);

        session.getTransaction().commit();
    }

    @Test
    void saveAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        teacherRepository = new TeacherRepository(session);
        var aboutCourse = UtilSave.buildTeacher();

        var actualEntity = teacherRepository.save(aboutCourse);
        session.evict(actualEntity);

        var list = teacherRepository.findAll();
        var expectedEntity = session.get(Teacher.class, UtilSave.idTeacher());
        assertThat(list).hasSize(4);
        assertThat(list).contains(expectedEntity);

        session.getTransaction().commit();
    }

    @Test
    void updateAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        teacherRepository = new TeacherRepository(session);
        var expectedTeacher = Teacher.builder()
                .id(UtilSave.idTeacher())
                .personalInfo(PersonalInfo.builder()
                        .email("newEmailTeacher@ya.ru")
                        .build())
                .build();

        teacherRepository.update(expectedTeacher);

        var actualTeacher = session.get(Teacher.class, UtilSave.idTeacher());
        assertThat(actualTeacher.toString()).isEqualTo(expectedTeacher.toString());

        session.getTransaction().commit();
    }
}
