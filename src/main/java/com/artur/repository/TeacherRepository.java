package com.artur.repository;

import com.artur.entity.Teacher;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherRepository extends RepositoryBase<Integer, Teacher>{
    public TeacherRepository(Session session) {
        super(Teacher.class, session);
    }
}
