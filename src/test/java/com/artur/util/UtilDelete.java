package com.artur.util;

import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;

@UtilityClass
public class UtilDelete {

    public void deleteData(SessionFactory sessionFactory) {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        session.createQuery("DELETE FROM Rating r")
                .executeUpdate();
        session.createQuery("DELETE FROM Student s")
                .executeUpdate();
        session.createQuery("DELETE FROM Course c")
                .executeUpdate();
        session.createQuery("DELETE FROM AboutCourse a")
                .executeUpdate();
        session.createQuery("DELETE FROM Teacher t")
                .executeUpdate();

        session.getTransaction().commit();

        UtilSave.getAboutCourses().clear();
        UtilSave.getTeachers().clear();
        UtilSave.getStudents().clear();
        UtilSave.getRatings().clear();
        UtilSave.getCourses().clear();
    }
}
