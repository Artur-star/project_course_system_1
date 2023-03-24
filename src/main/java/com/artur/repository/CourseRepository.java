package com.artur.repository;

import com.artur.entity.Course;
import org.hibernate.Session;

public class CourseRepository extends RepositoryBase<Long, Course>{
    public CourseRepository(Session session) {
        super(Course.class, session);
    }
}
