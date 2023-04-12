package com.artur.request;

import com.artur.dto.TeacherFilter;
import com.artur.database.entity.*;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.artur.database.entity.QAboutCourse.aboutCourse;
import static com.artur.database.entity.QCourse.course;
import static com.artur.database.entity.QRating.*;
import static com.artur.database.entity.QStudent.student;
import static com.artur.database.entity.QTeacher.teacher;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestQueryDsl {
    private static final RequestQueryDsl INSTANCE = new RequestQueryDsl();

    public static RequestQueryDsl getInstance() {
        return INSTANCE;
    }

    public List<Student> findAllStudentByCourseName(EntityManager entityManager, AboutCourse filter) {
        var predicate = QPredicate.builder()
                .add(filter.getName(), s -> aboutCourse.name.eq(filter.getName()))
                .buildAnd();


        return new JPAQuery<Student>(entityManager)
                .select(student)
                .from(student)
                .join(student.ratings, rating1)
                .join(rating1.course, course)
                .join(course.aboutCourse, aboutCourse)
                .where(predicate)
                .fetch();
    }

    public Long findCountStudentByTeacherNameAndSurname(EntityManager entityManager, TeacherFilter teacherFilter) {

        var predicate = QPredicate.builder()
                .add(teacherFilter.getFirstname(), it -> teacher.personalInfo.lastname.eq(teacherFilter.getLastname()))
                .add(teacherFilter.getLastname(), it -> teacher.personalInfo.firstname.eq(teacherFilter.getFirstname()))
                .buildAnd();

        return new JPAQuery<Student>(entityManager)
                .select(student.count())
                .from(student)
                .join(student.ratings, rating1)
                .join(rating1.course, course)
                .join(course.aboutCourse, aboutCourse)
                .join(aboutCourse.teacher, teacher)
                .where(predicate)
                .fetchOne();
    }

    //возвращает имя курса, отсортированный средний возраст студентов на этом курсе за все время
    public List<Tuple> findCourseNamesWithOrderedByAvgAgeStudents(EntityManager entityManager) {

        return new JPAQuery<Tuple>(entityManager)
                .select(aboutCourse.name, student.personalInfo.birthdate.year().avg())
                .from(aboutCourse)
                .join(aboutCourse.courses, course)
                .join(course.ratings, rating1)
                .join(rating1.student, student)
                .groupBy(aboutCourse)
                .orderBy(student.personalInfo.birthdate.year().avg().asc())
                .fetch();
    }
}
