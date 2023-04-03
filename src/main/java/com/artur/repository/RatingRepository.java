package com.artur.repository;

import com.artur.entity.Rating;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class RatingRepository extends RepositoryBase<Long, Rating>{
    public RatingRepository(EntityManager entityManager) {
        super(Rating.class, entityManager);
    }
}
