package com.artur.database.repository;

import com.artur.database.entity.AboutCourse;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class AboutCourseRepository extends RepositoryBase<Integer, AboutCourse> {
    public AboutCourseRepository(EntityManager entityManager) {
        super(AboutCourse.class, entityManager);
    }
}
