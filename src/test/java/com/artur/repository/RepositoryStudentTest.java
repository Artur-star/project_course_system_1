package com.artur.repository;

import com.artur.entity.PersonalInfo;
import com.artur.entity.Student;
import com.artur.util.HibernateUtil;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryStudentTest {
    SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();;
    StudentRepository studentRepository;

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

        studentRepository = new StudentRepository(session);

        var actualList = studentRepository.findAll();

        assertThat(actualList).hasSize(5);
        var expectedEntity1 = session.get(Student.class, 1L);
        var expectedEntity2 = session.get(Student.class, 2L);
        var expectedEntity3 = session.get(Student.class, 3L);
        assertThat(actualList).contains(expectedEntity1, expectedEntity2, expectedEntity3);

        session.getTransaction().commit();
    }

    @Test
    void findByIdAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        studentRepository = new StudentRepository(session);

        var actualStudent = studentRepository.findById(UtilSave.idStudent()).orElse(null);

        assertThat(actualStudent).isNotNull();

        session.getTransaction().commit();
    }

    @Test
    void deleteAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        studentRepository = new StudentRepository(session);
        var student = session.get(Student.class, UtilSave.idStudent());

        studentRepository.delete(student);

        var list = studentRepository.findAll();
        assertThat(list).hasSize(4);

        session.getTransaction().commit();
    }

    @Test
    void saveAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        studentRepository = new StudentRepository(session);
        var student = UtilSave.buildStudent();

        var actualEntity = studentRepository.save(student);
        session.evict(actualEntity);

        var list = studentRepository.findAll();
        var expectedEntity = session.get(Student.class, UtilSave.idStudent());
        assertThat(list).hasSize(6);
        assertThat(list).contains(expectedEntity);

        session.getTransaction().commit();
    }

    @Test
    void updateAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        studentRepository = new StudentRepository(session);
        var expectedStudent = Student.builder()
                .id(UtilSave.idStudent())
                .personalInfo(PersonalInfo.builder()
                        .email("newEmail@ya.ru").build())
                .build();

        studentRepository.update(expectedStudent);

        var actualStudent = session.get(Student.class, UtilSave.idStudent());
        assertThat(actualStudent.toString()).isEqualTo(expectedStudent.toString());

        session.getTransaction().commit();
    }
}
