package com.artur.database.repository;

import com.artur.annotation.IT;
import com.artur.database.entity.Rating;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class RepositoryRatingTest {
    private final EntityManager entityManager;
    private final RatingRepository ratingRepository;

    @BeforeEach
    void removeAndSaveData() {
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);
    }

    @Test
    void findAllRatingRepositoryTest() {
        var actualList = ratingRepository.findAll();

        assertThat(actualList).hasSize(10);
        var expectedEntity1 = entityManager.find(Rating.class, 1L);
        var expectedEntity2 = entityManager.find(Rating.class, 2L);
        var expectedEntity3 = entityManager.find(Rating.class, 3L);

        assertThat(actualList).contains(expectedEntity1, expectedEntity2, expectedEntity3);
    }

    @Test
    void findByIdRatingRepositoryTest() {
        var actualRating = ratingRepository.findById(UtilSave.idRating()).orElse(null);

        assertThat(actualRating).isNotNull();
    }

    @Test
    void deleteRatingRepositoryTest() {
        var rating = entityManager.find(Rating.class, UtilSave.idRating());

        ratingRepository.delete(rating);

        var actualResult = ratingRepository.findById(rating.getId()).orElse(null);
        assertThat(actualResult).isNull();
    }

    @Test
    void saveRatingRepositoryTest() {
        var rating = UtilSave.buildRating();

        var actualEntity = ratingRepository.save(rating);
        entityManager.detach(actualEntity);

        var list = ratingRepository.findAll();
        var expectedEntity = entityManager.find(Rating.class, UtilSave.idRating());
        assertThat(list).hasSize(11);
        assertThat(list).contains(expectedEntity);
    }

    @Test
    void updateRatingRepositoryTest() {
        var expectedRating = Rating.builder()
                .id(UtilSave.idRating())
                .rating((short) 4)
                .build();

        ratingRepository.update(expectedRating);

        var actualRating = entityManager.find(Rating.class, UtilSave.idRating());
        assertThat(actualRating.toString()).isEqualTo(expectedRating.toString());
    }
}
