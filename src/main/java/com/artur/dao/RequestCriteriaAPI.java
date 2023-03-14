package com.artur.dao;

import com.artur.dto.TeacherFilter;
import com.artur.entity.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import javax.persistence.criteria.*;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestCriteriaAPI {

    private static final RequestCriteriaAPI INSTANCE = new RequestCriteriaAPI();

    public static RequestCriteriaAPI getInstance() {
        return INSTANCE;
    }

    public List<Student> findAll(Session session) {
        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(Student.class);
        var student = criteria.from(Student.class);
        criteria.select(student);

        return session.createQuery(criteria)
                .list();
    }

    //максимальное количество студентов у преподавателя по имени и фамилии
    public List<Object[]> findMaxCountStudentByFirstnameAndLastnameTeacher(Session session) {
        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(Object[].class);

        var student = criteria.from(Student.class);
        var rating = student.join(Student_.ratings);
        var course = rating.join(Rating_.course);
        var aboutCourse = course.join(Course_.aboutCourse);
        var teacher = aboutCourse.join(AboutCourse_.teacher);

        criteria.multiselect(cb.count(student.get(Student_.id)),
                teacher.get(Teacher_.personalInfo).get(PersonalInfo_.firstname),
                teacher.get(Teacher_.personalInfo).get(PersonalInfo_.lastname))
        .groupBy(teacher.get(Teacher_.id));


        return session.createQuery(criteria)
                .list();
    }
}
