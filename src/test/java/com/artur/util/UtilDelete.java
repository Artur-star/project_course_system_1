package com.artur.util;

import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;

@UtilityClass
public class UtilDelete {

    public void deleteData(EntityManager entityManager) {
//        @Cleanup var session = sessionFactory.openSession();
//        session.beginTransaction();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Rating r")
                .executeUpdate();
        entityManager.createQuery("DELETE FROM Student s")
                .executeUpdate();
        entityManager.createQuery("DELETE FROM Course c")
                .executeUpdate();
        entityManager.createQuery("DELETE FROM AboutCourse a")
                .executeUpdate();
        entityManager.createQuery("DELETE FROM Teacher t")
                .executeUpdate();

        entityManager.getTransaction().commit();

        UtilSave.getAboutCourses().clear();
        UtilSave.getTeachers().clear();
        UtilSave.getStudents().clear();
        UtilSave.getRatings().clear();
        UtilSave.getCourses().clear();
    }
}
