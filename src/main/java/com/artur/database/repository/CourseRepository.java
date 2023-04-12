package com.artur.database.repository;

import com.artur.database.entity.Course;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class CourseRepository extends RepositoryBase<Long, Course>{
    public CourseRepository(EntityManager entityManager) {
        super(Course.class, entityManager);
    }
}
