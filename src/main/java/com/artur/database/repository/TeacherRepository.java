package com.artur.database.repository;

import com.artur.database.entity.Teacher;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class TeacherRepository extends RepositoryBase<Integer, Teacher>{
    public TeacherRepository(EntityManager entityManager) {
        super(Teacher.class, entityManager);
    }
}
