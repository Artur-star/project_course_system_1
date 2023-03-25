package com.artur.util;

import com.artur.entity.*;
import lombok.Cleanup;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.*;

@UtilityClass
@Data
public class UtilSave {
    private final List<Teacher> teachers = new ArrayList<>();
    private final List<AboutCourse> aboutCourses = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    private final List<Rating> ratings = new ArrayList<>();

    public void importData(SessionFactory sessionFactory) {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var webGlebMatveenka = saveTeacher(session, "Gleb", "Matveenka", "asd2@fer.com", "Веб-дизайнер");
        var qaVladVilnus = saveTeacher(session, "Vlad", "Vilnus", "dasdqw@fer.com", "QA-инженер");


        var webDev = saveAboutCourse(session, "Web-developer", webGlebMatveenka);
        var frontendDev = saveAboutCourse(session, "Frontend-developer on Python", webGlebMatveenka);
        var qaDev = saveAboutCourse(session, "QA-developer", qaVladVilnus);



        var course = saveCourse(session, of(2021, 2, 10), of(2022, 2, 10), webDev);
        var course1 = saveCourse(session, of(2021, 5, 20), of(2022, 5, 20), webDev);
        var course2 = saveCourse(session, of(2022, 3, 10), of(2023, 3, 10), frontendDev);
        var course3 = saveCourse(session, of(2022, 6, 10), of(2023, 6, 10), qaDev);


        var antonGyev = saveStudent(session, "Anton", "Gyev", "aaa@asd.ru", of(1974, 11, 11));
        var andreyRobich = saveStudent(session, "Andrey", "Robich", "aaa1@asd.ru",  of(1984, 11, 11));
        var alexKeln = saveStudent(session, "Alex", "Keln", "aaa2@asd.ru",  of(1994, 11, 11));
        var alfredGaag = saveStudent(session, "Alfred", "Gaag", "aaa3@asd.ru",  of(1995, 11, 11));
        var albertDobrev = saveStudent(session, "Albert", "Dobrev", "aaa4@asd.ru",  of(1999, 11, 11));


        var rating = saveRating(session, course, antonGyev, (short) 5);
        var rating1 = saveRating(session, course, andreyRobich, (short) 5);
        var rating2 = saveRating(session, course, alexKeln, (short) 4);
        var rating3 = saveRating(session, course1, albertDobrev, (short) 4);
        var rating4 = saveRating(session, course1, antonGyev, (short) 5);
        var rating5 = saveRating(session, course1, alexKeln, (short) 4);
        var rating6 = saveRating(session, course2, alfredGaag, (short) 5);
        var rating7 = saveRating(session, course2, albertDobrev, (short) 4);
        var rating8 = saveRating(session, course3, andreyRobich, (short) 5);
        var rating9 = saveRating(session, course3, alfredGaag, (short) 3);

        teachers.add(webGlebMatveenka);
        teachers.add(qaVladVilnus);

        aboutCourses.add(webDev);
        aboutCourses.add(frontendDev);
        aboutCourses.add(qaDev);

        courses.add(course);
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);

        students.add(antonGyev);
        students.add(andreyRobich);
        students.add(alexKeln);
        students.add(alfredGaag);
        students.add(albertDobrev);

        ratings.add(rating);
        ratings.add(rating1);
        ratings.add(rating2);
        ratings.add(rating3);
        ratings.add(rating4);
        ratings.add(rating5);
        ratings.add(rating6);
        ratings.add(rating7);
        ratings.add(rating8);
        ratings.add(rating9);

        session.getTransaction().commit();
    }

    public Integer idTeacher() {
        return teachers.get(0).getId();
    }

    public Integer idAboutCourse() {
        return aboutCourses.get(0).getId();
    }

    public Long idStudent() {
        return students.get(0).getId();
    }

    public Long idCourse() {
        return courses.get(0).getId();
    }

    public Long idRating() {
        return ratings.get(0).getId();
    }

    private Rating saveRating(Session session, Course course, Student student, Short estimate) {
        var rating = Rating.builder()
                .course(course)
                .student(student)
                .rating(estimate)
                .build();
        session.save(rating);
        return rating;
    }

    private Course saveCourse(Session session , LocalDate start, LocalDate finish, AboutCourse aboutCourse) {
        var course = Course.builder()
                .aboutCourse(aboutCourse)
                .start(start)
                .finish(finish)
                .build();
        session.save(course);
        return course;
    }

    private Teacher saveTeacher(Session session, String firstname, String lastname, String email, String profession) {
        var teacher = Teacher.builder()
                .personalInfo(PersonalInfo.builder()
                        .firstname(firstname)
                        .lastname(lastname)
                        .patronymic("Гонсалес")
                        .email(email)
                        .birthdate(of(1975, 9, 16))
                        .build())
                .profession(profession)
                .build();

        session.save(teacher);
        return teacher;
    }

    private Student saveStudent(Session session, String firstname, String lastname, String email, LocalDate birthday) {
        var student = Student.builder()
                .personalInfo(PersonalInfo.builder()
                        .firstname(firstname)
                        .lastname(lastname)
                        .patronymic("Артурович")
                        .email(email)
                        .birthdate(birthday)
                        .build())
                .build();
        session.save(student);
        return student;
    }

    private AboutCourse saveAboutCourse(Session session, String name , Teacher teacher) {
        var aboutCourse = AboutCourse.builder()
                .teacher(teacher)
                .name(name)
                .costInRubles(80000)
                .maxStudentsNumber(5)
                .build();
        session.save(aboutCourse);
        return aboutCourse;
    }

    public Rating buildRating() {
        return Rating.builder()
                .rating((short) 5)
                .build();
    }

    public AboutCourse buildAboutCourse() {
        return AboutCourse.builder()
                .name("Веб-разработчик")
                .costInRubles(80000)
                .maxStudentsNumber(5)
                .build();
    }

    public Course buildCourse() {
        return Course.builder()
                .start(of(2022, 10, 10))
                .finish(of(2023, 10, 10))
                .build();
    }

    public Student buildStudent() {
        return Student.builder()
                .personalInfo(PersonalInfo.builder()
                        .firstname("Антуан")
                        .lastname("Гризман")
                        .patronymic("Артурович")
                        .email("antuan@gmail.com")
                        .birthdate(of(1990, 9, 16))
                        .build())
                .build();
    }

    public Teacher buildTeacher() {
        return Teacher.builder()
                .personalInfo(PersonalInfo.builder()
                        .firstname("Диего")
                        .lastname("Симеоне")
                        .patronymic("Гонсалес")
                        .email("YEFA@gmail.com")
                        .birthdate(of(1975, 9, 16))
                        .build())
                .profession("Веб-дизайнер")
                .build();
    }

    public List<AboutCourse> getAboutCourses() {
        return aboutCourses;
    }
}
