package com.artur.repository;

import com.artur.entity.Course;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CourseRepository extends RepositoryBase<Long, Course>{
    public CourseRepository(EntityManager entityManager) {
        super(Course.class, entityManager);
    }
}
