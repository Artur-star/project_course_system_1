package com.artur.database.repository;

import com.artur.database.entity.Student;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class StudentRepository extends RepositoryBase<Long, Student>{
    public StudentRepository(EntityManager entityManager) {
        super(Student.class, entityManager);
    }
}
