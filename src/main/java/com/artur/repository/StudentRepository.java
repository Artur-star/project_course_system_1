package com.artur.repository;

import com.artur.entity.Student;
import org.hibernate.Session;

public class StudentRepository extends RepositoryBase<Long, Student>{
    public StudentRepository(Session session) {
        super(Student.class, session);
    }
}
