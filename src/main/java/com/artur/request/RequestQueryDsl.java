package com.artur.request;

import com.artur.dto.TeacherFilter;
import com.artur.entity.*;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

import static com.artur.entity.QAboutCourse.aboutCourse;
import static com.artur.entity.QCourse.course;
import static com.artur.entity.QRating.*;
import static com.artur.entity.QStudent.student;
import static com.artur.entity.QTeacher.teacher;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestQueryDsl {
    private static final RequestQueryDsl INSTANCE = new RequestQueryDsl();

    public static RequestQueryDsl getInstance() {
        return INSTANCE;
    }

    public List<Student> findAllStudentByCourseName(Session session, AboutCourse filter) {
        var predicate = QPredicate.builder()
                .add(filter.getName(), s -> aboutCourse.name.eq(filter.getName()))
                .buildAnd();


        return new JPAQuery<Student>(session)
                .select(student)
                .from(student)
                .join(student.ratings, rating1)
                .join(rating1.course, course)
                .join(course.aboutCourse, aboutCourse)
                .where(predicate)
                .fetch();
    }

    public Long findCountStudentByTeacherNameAndSurname(Session session, TeacherFilter teacherFilter) {

        var predicate = QPredicate.builder()
                .add(teacherFilter.getFirstname(), it -> teacher.personalInfo.lastname.eq(teacherFilter.getLastname()))
                .add(teacherFilter.getLastname(), it -> teacher.personalInfo.firstname.eq(teacherFilter.getFirstname()))
                .buildAnd();

        return new JPAQuery<Student>(session)
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
    public List<Tuple> findCourseNamesWithOrderedByAvgAgeStudents(Session session) {

        return new JPAQuery<Tuple>(session)
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
