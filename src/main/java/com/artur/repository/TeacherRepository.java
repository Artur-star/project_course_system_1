package com.artur.repository;

import com.artur.entity.Teacher;
import org.hibernate.Session;

public class TeacherRepository extends RepositoryBase<Integer, Teacher>{
    public TeacherRepository(Session session) {
        super(Teacher.class, session);
    }
}
