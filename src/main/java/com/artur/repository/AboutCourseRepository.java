package com.artur.repository;

import com.artur.entity.AboutCourse;
import org.hibernate.Session;

public class AboutCourseRepository extends RepositoryBase<Integer, AboutCourse> {
    public AboutCourseRepository(Session session) {
        super(AboutCourse.class, session);
    }
}
