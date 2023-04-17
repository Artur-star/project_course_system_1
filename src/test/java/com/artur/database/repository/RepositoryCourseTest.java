package com.artur.database.repository;

import com.artur.annotation.IT;
import com.artur.database.entity.Course;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class RepositoryCourseTest {
    private final EntityManager entityManager;
    private final CourseRepository courseRepository;

    @BeforeEach
    void removeAndSaveData() {
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);
    }

    @Test
    void findAllCourseRepositoryTest() {
        var actualList = courseRepository.findAll();

        assertThat(actualList).hasSize(4);
        var expectedEntity1 = entityManager.find(Course.class, 1L);
        var expectedEntity2 = entityManager.find(Course.class, 2L);
        var expectedEntity3 = entityManager.find(Course.class, 3L);
        var expectedEntity4 = entityManager.find(Course.class, 4L);

        assertThat(actualList).contains(expectedEntity1, expectedEntity2, expectedEntity3, expectedEntity4);
    }

    @Test
    void findByIdCourseRepositoryTest() {
        var actualAboutCourse = courseRepository.findById(UtilSave.idCourse()).orElse(null);

        assertThat(actualAboutCourse).isNotNull();
    }

    @Test
    void deleteCourseRepositoryTest() {
        var course = entityManager.find(Course.class, UtilSave.idCourse());

        courseRepository.delete(course);

        var actualResult = courseRepository.findById(course.getId()).orElse(null);

        assertThat(actualResult).isNull();
    }

    @Test
    void saveCourseRepositoryTest() {
        var course = UtilSave.buildCourse();

        var actualEntity = courseRepository.save(course);
        entityManager.detach(actualEntity);

        var list = courseRepository.findAll();
        var expectedEntity = entityManager.find(Course.class, UtilSave.idCourse());
        assertThat(list).hasSize(5);
        assertThat(list).contains(expectedEntity);
    }

    @Test
    void updateCourseRepositoryTest() {
        var expectedCourse = Course.builder()
                .id(UtilSave.idCourse())
                .start(LocalDate.of(2020, 10, 1))
                .finish(LocalDate.of(2021, 10, 1))
                .build();

        courseRepository.update(expectedCourse);

        var actualCourse = entityManager.find(Course.class, UtilSave.idCourse());
        assertThat(actualCourse.toString()).isEqualTo(expectedCourse.toString());
    }
}
