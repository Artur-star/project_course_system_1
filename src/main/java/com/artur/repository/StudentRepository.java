package com.artur.repository;

import com.artur.entity.Student;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class StudentRepository extends RepositoryBase<Long, Student>{
    public StudentRepository(EntityManager entityManager) {
        super(Student.class, entityManager);
    }
}
