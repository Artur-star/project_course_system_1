package com.artur.repository;

import com.artur.entity.Rating;
import org.hibernate.Session;

public class RatingRepository extends RepositoryBase<Long, Rating>{
    public RatingRepository(Session session) {
        super(Rating.class, session);
    }
}
