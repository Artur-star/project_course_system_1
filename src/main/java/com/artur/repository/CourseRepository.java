package com.artur.repository;

import com.artur.entity.Course;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CourseRepository extends RepositoryBase<Long, Course>{
    public CourseRepository(Session session) {
        super(Course.class, session);
    }
}
