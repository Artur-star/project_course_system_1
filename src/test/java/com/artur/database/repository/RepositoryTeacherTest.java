package com.artur.database.repository;

import com.artur.annotation.IT;
import com.artur.database.entity.PersonalInfo;
import com.artur.database.entity.Teacher;
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
public class RepositoryTeacherTest {
    private final EntityManager entityManager;
    private final TeacherRepository teacherRepository;

    @BeforeEach
    void removeAndSaveData() {
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);
    }

    @Test
    void findAllTeacherRepositoryTest() {
        var actualList = teacherRepository.findAll();

        assertThat(actualList).hasSize(2);
        var expectedEntity1 = entityManager.find(Teacher.class, UtilSave.idTeacher());
        var expectedEntity2 = entityManager.find(Teacher.class, UtilSave.idTeacher());

        assertThat(actualList).contains(expectedEntity1, expectedEntity2);
    }

    @Test
    void findByIdTeacherRepositoryTest() {
        var actualTeacher = teacherRepository.findById(UtilSave.idTeacher()).orElse(null);

        assertThat(actualTeacher).isNotNull();
    }

    @Test
    void deleteTeacherRepositoryTest() {
        var teacher = entityManager.find(Teacher.class, UtilSave.idTeacher());

        teacherRepository.delete(teacher);

        var actualResult = teacherRepository.findById(teacher.getId()).orElse(null);
        assertThat(actualResult).isNull();
    }

    @Test
    void saveTeacherRepositoryTest() {
        var aboutCourse = UtilSave.buildTeacher();

        var actualEntity = teacherRepository.save(aboutCourse);
        entityManager.detach(actualEntity);

        var list = teacherRepository.findAll();
        var expectedEntity = entityManager.find(Teacher.class, UtilSave.idTeacher());
        assertThat(list).hasSize(3);
        assertThat(list).contains(expectedEntity);
    }

    @Test
    void updateTeacherRepositoryTest() {
        var expectedTeacher = Teacher.builder()
                .id(UtilSave.idTeacher())
                .personalInfo(PersonalInfo.builder()
                        .email("newEmailTeacher@ya.ru")
                        .build())
                .build();

        teacherRepository.update(expectedTeacher);
        var actualTeacher = entityManager.find(Teacher.class, UtilSave.idTeacher());

        assertThat(actualTeacher.toString()).isEqualTo(expectedTeacher.toString());
    }
}
