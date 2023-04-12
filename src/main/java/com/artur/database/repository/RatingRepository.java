package com.artur.database.repository;

import com.artur.database.entity.Rating;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class RatingRepository extends RepositoryBase<Long, Rating>{
    public RatingRepository(EntityManager entityManager) {
        super(Rating.class, entityManager);
    }
}
