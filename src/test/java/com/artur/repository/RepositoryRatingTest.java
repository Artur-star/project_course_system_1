package com.artur.repository;

import com.artur.config.ApplicationConfigurationTest;
import com.artur.entity.Rating;
import com.artur.util.HibernateTestUtil;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryRatingTest {
    AnnotationConfigApplicationContext context;
    SessionFactory sessionFactory;
    EntityManager entityManager;
    RatingRepository ratingRepository;


    @BeforeAll
    void startAll() {
        context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
        sessionFactory = context.getBean("sessionFactory", SessionFactory.class);
        entityManager = context.getBean("entityManager", EntityManager.class);
    }

    @Test
    void findAllRatingRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(sessionFactory);
        UtilSave.importData(sessionFactory);

        ratingRepository = new RatingRepository(entityManager);

        var actualList = ratingRepository.findAll();

        assertThat(actualList).hasSize(10);
        var expectedEntity1 = entityManager.find(Rating.class, 1L);
        var expectedEntity2 = entityManager.find(Rating.class, 2L);
        var expectedEntity3 = entityManager.find(Rating.class, 3L);
        assertThat(actualList).contains(expectedEntity1, expectedEntity2, expectedEntity3);

        entityManager.getTransaction().commit();
    }

    @Test
    void findByIdRatingRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(sessionFactory);
        UtilSave.importData(sessionFactory);

        ratingRepository = new RatingRepository(entityManager);

        var actualRating = ratingRepository.findById(UtilSave.idRating()).orElse(null);

        assertThat(actualRating).isNotNull();

        entityManager.getTransaction().commit();
    }

    @Test
    void deleteRatingRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(sessionFactory);
        UtilSave.importData(sessionFactory);

        ratingRepository = new RatingRepository(entityManager);
        var rating = entityManager.find(Rating.class, UtilSave.idRating());

        ratingRepository.delete(rating);

        var list = ratingRepository.findAll();
        assertThat(list).hasSize(9);

        entityManager.getTransaction().commit();
    }

    @Test
    void saveRatingRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(sessionFactory);
        UtilSave.importData(sessionFactory);

        ratingRepository = new RatingRepository(entityManager);
        var rating = UtilSave.buildRating();

        var actualEntity = ratingRepository.save(rating);
        entityManager.detach(actualEntity);

        var list = ratingRepository.findAll();
        var expectedEntity = entityManager.find(Rating.class, UtilSave.idRating());
        assertThat(list).hasSize(11);
        assertThat(list).contains(expectedEntity);

        entityManager.getTransaction().commit();
    }

    @Test
    void updateRatingRepositoryTest() {
        entityManager.getTransaction().begin();
        UtilDelete.deleteData(sessionFactory);
        UtilSave.importData(sessionFactory);

        ratingRepository = new RatingRepository(entityManager);
        var expectedRating = Rating.builder()
                .id(UtilSave.idRating())
                .rating((short) 4)
                .build();

        ratingRepository.update(expectedRating);

        var actualRating = entityManager.find(Rating.class, UtilSave.idRating());
        assertThat(actualRating.toString()).isEqualTo(expectedRating.toString());

        entityManager.getTransaction().commit();
    }
}
