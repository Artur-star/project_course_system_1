package com.artur.repository;

import com.artur.entity.Rating;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RatingRepository extends RepositoryBase<Long, Rating>{
    public RatingRepository(Session session) {
        super(Rating.class, session);
    }
}
