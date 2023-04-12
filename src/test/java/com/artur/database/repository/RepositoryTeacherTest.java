package com.artur.database.repository;

import com.artur.config.ApplicationConfigurationTest;
import com.artur.database.entity.PersonalInfo;
import com.artur.database.entity.Teacher;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryTeacherTest {

    AnnotationConfigApplicationContext context;
    SessionFactory sessionFactory;
    EntityManager entityManager;
    TeacherRepository teacherRepository;


    @BeforeAll
    void startAll() {
        context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
        sessionFactory = context.getBean("sessionFactory", SessionFactory.class);
        entityManager = context.getBean("entityManager", EntityManager.class);
    }

    @Test
    void findAllTeacherRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

        teacherRepository = new TeacherRepository(entityManager);

        var actualList = teacherRepository.findAll();

        assertThat(actualList).hasSize(2);
        var expectedEntity1 = entityManager.find(Teacher.class, UtilSave.idTeacher());
        var expectedEntity2 = entityManager.find(Teacher.class, UtilSave.idTeacher());
        assertThat(actualList).contains(expectedEntity1, expectedEntity2);

        entityManager.getTransaction().commit();
    }

    @Test
    void findByIdTeacherRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

        teacherRepository = new TeacherRepository(entityManager);

        var actualTeacher = teacherRepository.findById(UtilSave.idTeacher()).orElse(null);

        assertThat(actualTeacher).isNotNull();

        entityManager.getTransaction().commit();
    }

    @Test
    void deleteTeacherRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

        teacherRepository = new TeacherRepository(entityManager);
        var teacher = entityManager.find(Teacher.class, UtilSave.idTeacher());

        teacherRepository.delete(teacher);

        var list = teacherRepository.findAll();
        assertThat(list).hasSize(1);

        entityManager.getTransaction().commit();
    }

    @Test
    void saveTeacherRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

        teacherRepository = new TeacherRepository(entityManager);
        var aboutCourse = UtilSave.buildTeacher();

        var actualEntity = teacherRepository.save(aboutCourse);
        entityManager.detach(actualEntity);

        var list = teacherRepository.findAll();
        var expectedEntity = entityManager.find(Teacher.class, UtilSave.idTeacher());
        assertThat(list).hasSize(3);
        assertThat(list).contains(expectedEntity);

        entityManager.getTransaction().commit();
    }

    @Test
    void updateTeacherRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

        teacherRepository = new TeacherRepository(entityManager);
        var expectedTeacher = Teacher.builder()
                .id(UtilSave.idTeacher())
                .personalInfo(PersonalInfo.builder()
                        .email("newEmailTeacher@ya.ru")
                        .build())
                .build();

        teacherRepository.update(expectedTeacher);

        var actualTeacher = entityManager.find(Teacher.class, UtilSave.idTeacher());
        assertThat(actualTeacher.toString()).isEqualTo(expectedTeacher.toString());

        entityManager.getTransaction().commit();
    }
}
