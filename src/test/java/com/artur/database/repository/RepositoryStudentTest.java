package com.artur.database.repository;

import com.artur.config.ApplicationConfigurationTest;
import com.artur.database.entity.PersonalInfo;
import com.artur.database.entity.Student;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryStudentTest {
    AnnotationConfigApplicationContext context;
    SessionFactory sessionFactory;
    EntityManager entityManager;
    StudentRepository studentRepository;


    @BeforeAll
    void startAll() {
        context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
        sessionFactory = context.getBean("sessionFactory", SessionFactory.class);
        entityManager = context.getBean("entityManager", EntityManager.class);
    }

    @Test
    void findAllStudentRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

        studentRepository = new StudentRepository(entityManager);

        var actualList = studentRepository.findAll();

        assertThat(actualList).hasSize(5);
        var expectedEntity1 = entityManager.find(Student.class, UtilSave.idStudent());
        var expectedEntity2 = entityManager.find(Student.class, UtilSave.idStudent());
        var expectedEntity3 = entityManager.find(Student.class, UtilSave.idStudent());
        assertThat(actualList).contains(expectedEntity1, expectedEntity2, expectedEntity3);

        entityManager.getTransaction().commit();
    }

    @Test
    void findByIdStudentRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

        studentRepository = new StudentRepository(entityManager);

        var actualStudent = studentRepository.findById(UtilSave.idStudent()).orElse(null);

        assertThat(actualStudent).isNotNull();

        entityManager.getTransaction().commit();
    }

    @Test
    void deleteStudentRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

        studentRepository = new StudentRepository(entityManager);
        var student = entityManager.find(Student.class, UtilSave.idStudent());

        studentRepository.delete(student);

        var list = studentRepository.findAll();
        assertThat(list).hasSize(4);

        entityManager.getTransaction().commit();
    }

    @Test
    void saveStudentRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

        studentRepository = new StudentRepository(entityManager);
        var student = UtilSave.buildStudent();

        var actualEntity = studentRepository.save(student);
        entityManager.detach(actualEntity);

        var list = studentRepository.findAll();
        var expectedEntity = entityManager.find(Student.class, UtilSave.idStudent());
        assertThat(list).hasSize(6);
        assertThat(list).contains(expectedEntity);

        entityManager.getTransaction().commit();
    }

    @Test
    void updateStudentRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);

        studentRepository = new StudentRepository(entityManager);
        var expectedStudent = Student.builder()
                .id(UtilSave.idStudent())
                .personalInfo(PersonalInfo.builder()
                        .email("newEmail@ya.ru").build())
                .build();

        studentRepository.update(expectedStudent);

        var actualStudent = entityManager.find(Student.class, UtilSave.idStudent());
        assertThat(actualStudent.toString()).isEqualTo(expectedStudent.toString());

        entityManager.getTransaction().commit();
    }
}
