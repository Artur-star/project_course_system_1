package com.artur.request;

import com.artur.database.entity.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestCriteriaAPI {

    private static final RequestCriteriaAPI INSTANCE = new RequestCriteriaAPI();

    public static RequestCriteriaAPI getInstance() {
        return INSTANCE;
    }

    public List<Student> findAll(EntityManager entityManager) {
        var cb = entityManager.getCriteriaBuilder();

        var criteria = cb.createQuery(Student.class);
        var student = criteria.from(Student.class);
        criteria.select(student);

        return entityManager.createQuery(criteria)
                .getResultList();
    }

    //максимальное количество студентов у преподавателя по имени и фамилии
    public List<Tuple> findMaxCountStudentByFirstnameAndLastnameTeacher(EntityManager entityManager, String firstname, String lastname) {
        var cb = entityManager.getCriteriaBuilder();

        var criteria = cb.createQuery(Tuple.class);

        var student = criteria.from(Student.class);
        var rating = student.join(Student_.ratings);
        var course = rating.join(Rating_.course);
        var aboutCourse = course.join(Course_.aboutCourse);
        var teacher = aboutCourse.join(AboutCourse_.teacher);

        List<Predicate> predicates = new ArrayList<>();
        if(firstname != null) {
            predicates.add(cb.equal(teacher.get(Teacher_.personalInfo).get(PersonalInfo_.firstname), firstname));
        } if(lastname != null) {
            predicates.add(cb.equal(teacher.get(Teacher_.personalInfo).get(PersonalInfo_.lastname), lastname));
        }

        criteria.select(cb.tuple(
                cb.count(student.get(Student_.id)),
                teacher.get(Teacher_.personalInfo).get(PersonalInfo_.firstname),
                teacher.get(Teacher_.personalInfo).get(PersonalInfo_.lastname))).where(
                predicates.toArray(Predicate[]::new))
                .groupBy(teacher.get(Teacher_.id));

        return entityManager.createQuery(criteria)
                .getResultList();
    }
}
