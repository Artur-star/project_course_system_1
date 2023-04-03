package com.artur.repository;

import com.artur.entity.Teacher;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class TeacherRepository extends RepositoryBase<Integer, Teacher>{
    public TeacherRepository(EntityManager entityManager) {
        super(Teacher.class, entityManager);
    }
}
