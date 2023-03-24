package com.artur.repository;

import com.artur.entity.Rating;
import com.artur.util.HibernateUtil;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryRatingTest {
    SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();;
    RatingRepository ratingRepository;

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

        ratingRepository = new RatingRepository(session);

        var actualList = ratingRepository.findAll();

        assertThat(actualList).hasSize(6);
        var expectedEntity1 = session.get(Rating.class, 1L);
        var expectedEntity2 = session.get(Rating.class, 2L);
        var expectedEntity3 = session.get(Rating.class, 3L);
        assertThat(actualList).contains(expectedEntity1, expectedEntity2, expectedEntity3);

        session.getTransaction().commit();
    }

    @Test
    void findByIdAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        ratingRepository = new RatingRepository(session);

        var actualRating = ratingRepository.findById(UtilSave.idRating()).orElse(null);

        assertThat(actualRating).isNotNull();

        session.getTransaction().commit();
    }

    @Test
    void deleteAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        ratingRepository = new RatingRepository(session);
        var rating = session.get(Rating.class, UtilSave.idRating());

        ratingRepository.delete(rating);

        var list = ratingRepository.findAll();
        assertThat(list).hasSize(5);

        session.getTransaction().commit();
    }

    @Test
    void saveAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        ratingRepository = new RatingRepository(session);
        var rating = UtilSave.buildRating();

        var actualEntity = ratingRepository.save(rating);
        session.evict(actualEntity);

        var list = ratingRepository.findAll();
        var expectedEntity = session.get(Rating.class, UtilSave.idRating());
        assertThat(list).hasSize(7);
        assertThat(list).contains(expectedEntity);

        session.getTransaction().commit();
    }

    @Test
    void updateAboutCourseRepositoryTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        ratingRepository = new RatingRepository(session);
        var expectedRating = Rating.builder()
                .id(UtilSave.idRating())
                .rating((short) 4)
                .build();

        ratingRepository.update(expectedRating);

        var actualRating = session.get(Rating.class, UtilSave.idRating());
        assertThat(actualRating.toString()).isEqualTo(expectedRating.toString());

        session.getTransaction().commit();
    }
}
