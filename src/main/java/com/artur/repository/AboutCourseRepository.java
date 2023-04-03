package com.artur.repository;

import com.artur.entity.AboutCourse;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AboutCourseRepository extends RepositoryBase<Integer, AboutCourse> {
    public AboutCourseRepository(EntityManager entityManager) {
        super(AboutCourse.class, entityManager);
    }
}
