package com.artur.database.repository;

import com.artur.annotation.IT;
import com.artur.database.entity.PersonalInfo;
import com.artur.database.entity.Student;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class RepositoryStudentTest {
    private final EntityManager entityManager;
    private final StudentRepository studentRepository;

    @BeforeEach
    void removeAndSaveData() {
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);
    }

    @Test
    void findAllStudentRepositoryTest() {
        var actualList = studentRepository.findAll();

        assertThat(actualList).hasSize(5);
        var expectedEntity1 = entityManager.find(Student.class, UtilSave.idStudent());
        var expectedEntity2 = entityManager.find(Student.class, UtilSave.idStudent());
        var expectedEntity3 = entityManager.find(Student.class, UtilSave.idStudent());

        assertThat(actualList).contains(expectedEntity1, expectedEntity2, expectedEntity3);
    }

    @Test
    void findByIdStudentRepositoryTest() {
        var actualStudent = studentRepository.findById(UtilSave.idStudent()).orElse(null);

        assertThat(actualStudent).isNotNull();
    }

    @Test
    void deleteStudentRepositoryTest() {
        var student = entityManager.find(Student.class, UtilSave.idStudent());

        studentRepository.delete(student);

        var actualResult = studentRepository.findById(student.getId()).orElse(null);
        assertThat(actualResult).isNull();
    }

    @Test
    void saveStudentRepositoryTest() {
        var student = UtilSave.buildStudent();

        var actualEntity = studentRepository.save(student);
        entityManager.detach(actualEntity);

        var list = studentRepository.findAll();
        var expectedEntity = entityManager.find(Student.class, UtilSave.idStudent());
        assertThat(list).hasSize(6);
        assertThat(list).contains(expectedEntity);
    }

    @Test
    void updateStudentRepositoryTest() {
        var expectedStudent = Student.builder()
                .id(UtilSave.idStudent())
                .personalInfo(PersonalInfo.builder()
                        .email("newEmail@ya.ru").build())
                .build();

        studentRepository.update(expectedStudent);

        var actualStudent = entityManager.find(Student.class, UtilSave.idStudent());
        assertThat(actualStudent.toString()).isEqualTo(expectedStudent.toString());
    }
}
