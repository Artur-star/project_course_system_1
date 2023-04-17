package com.artur.database.repository;

import com.artur.annotation.IT;
import com.artur.database.entity.AboutCourse;
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
public class RepositoryAboutCourseTest {
    private final EntityManager entityManager;
    private final AboutCourseRepository aboutCourseRepository;

    @BeforeEach
    void removeAndSaveData() {
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);
    }

    @Test
    void findAllAboutCourseRepositoryTest() {
        var actualList = aboutCourseRepository.findAll();

        assertThat(actualList).hasSize(3);
    }

    @Test
    void findByIdAboutCourseRepositoryTest() {
        var actualAboutCourse = aboutCourseRepository.findById(UtilSave.idAboutCourse()).orElse(null);

        assertThat(actualAboutCourse).isNotNull();
    }

    @Test
    void deleteAboutCourseRepositoryTest() {
        System.out.println();
        var aboutCourse = entityManager.find(AboutCourse.class, UtilSave.idAboutCourse());

        aboutCourseRepository.delete(aboutCourse);
        var actualResult = aboutCourseRepository.findById(aboutCourse.getId()).orElse(null);

        assertThat(actualResult).isNull();
    }

    @Test
    void saveAboutCourseRepositoryTest() {
        var aboutCourse = UtilSave.buildAboutCourse();

        var actualEntity = aboutCourseRepository.save(aboutCourse);
        entityManager.detach(actualEntity);

        var list = aboutCourseRepository.findAll();
        var expectedEntity = entityManager.find(AboutCourse.class, UtilSave.idAboutCourse());

        assertThat(list).hasSize(4);
        assertThat(list).contains(expectedEntity);
    }

    @Test
    void updateAboutCourseRepositoryTest() {
        var expectedAboutCourse = AboutCourse.builder()
                .id(UtilSave.idAboutCourse())
                .name("JavaScript-разработчик")
                .costInRubles(60000)
                .maxStudentsNumber(6)
                .build();

        aboutCourseRepository.update(expectedAboutCourse);

        var actualAboutCourse = entityManager.find(AboutCourse.class, UtilSave.idAboutCourse());
        assertThat(actualAboutCourse.toString()).isEqualTo(expectedAboutCourse.toString());
    }
}
