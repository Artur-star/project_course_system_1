package com.artur.repository;

import com.artur.entity.Student;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository extends RepositoryBase<Long, Student>{
    public StudentRepository(Session session) {
        super(Student.class, session);
    }
}
