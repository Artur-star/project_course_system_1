package com.artur.repository;

import com.artur.entity.AboutCourse;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AboutCourseRepository extends RepositoryBase<Integer, AboutCourse> {
    public AboutCourseRepository(Session session) {
        super(AboutCourse.class, session);
    }
}
