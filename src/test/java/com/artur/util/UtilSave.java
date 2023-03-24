package com.artur.util;

import com.artur.entity.*;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

@UtilityClass
public class UtilSave {
    private static Teacher teacher;
    private static AboutCourse aboutCourse;
    private static Student student;
    private static Course course;
    private static Rating rating;

    public void importData(SessionFactory sessionFactory) {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        teacher = saveTeacher(session, "Gleb", "Matveenka", "asd2@fer.com", "Веб-дизайнер");
        var teacher1 = saveTeacher(session, "Vlad", "Vilnus", "dasdqw@fer.com", "QA-инженер");
        var teacher2 = saveTeacher(session, "Ivan", "Ivanov", "asdaqw@fer.com", "Flutter-developer");

        aboutCourse = saveAboutCourse(session, "Веб-разработчик");
        var aboutCourse1 = saveAboutCourse(session, "QA-инженер");
        var aboutCourse2 = saveAboutCourse(session, "Flutter-developer");

        student = saveStudent(session, "Anton", "Gyev", "aaa@asd.ru");
        var student1 = saveStudent(session, "Andrey", "Robich", "aaa1@asd.ru");
        var student2 = saveStudent(session, "Alex", "Keln", "aaa2@asd.ru");
        var student3 = saveStudent(session, "Alfred", "Gaag", "aaa3@asd.ru");
        var student4 = saveStudent(session, "Albert", "Dobrev", "aaa4@asd.ru");

        course = saveCourse(session);
        var course1 = saveCourse(session);
        var course2 = saveCourse(session);
        var course3 = saveCourse(session);

        rating = saveRating(session);
        var rating1 = saveRating(session);
        var rating2 = saveRating(session);
        var rating3 = saveRating(session);
        var rating4 = saveRating(session);
        var rating5 = saveRating(session);

        UtilSave.relationshipBetweenEntity();

        session.getTransaction().commit();
    }

    public void relationshipBetweenEntity() {
        teacher.setAboutCourse(aboutCourse);
        aboutCourse.addAboutCourse(course);
        rating.setStudent(student);
        rating.setCourse(course);
    }

    public Integer idTeacher() {
        return teacher.getId();
    }

    public Integer idAboutCourse() {
        return aboutCourse.getId();
    }

    public Long idStudent() {
        return student.getId();
    }

    public Long idCourse() {
        return student.getId();
    }

    public Long idRating() {
        return rating.getId();
    }

    public static AboutCourse getAboutCourse() {
        return aboutCourse;
    }

    public static Teacher getTeacher() {
        return teacher;
    }

    private Rating saveRating(Session session) {
        var rating = Rating.builder()
                .rating((short) 5)
                .build();
        session.save(rating);
        return rating;
    }

    private Course saveCourse(Session session) {
        var course = Course.builder()
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
                        .birthdate(LocalDate.of(1975, 9, 16))
                        .build())
                .profession(profession)
                .build();

        session.save(teacher);
        return teacher;
    }

    private Student saveStudent(Session session, String firstname, String lastname, String email) {
        var student = Student.builder()
                .personalInfo(PersonalInfo.builder()
                        .firstname(firstname)
                        .lastname(lastname)
                        .patronymic("Артурович")
                        .email(email)
                        .birthdate(LocalDate.of(1990, 9, 16))
                        .build())
                .build();
        session.save(student);
        return student;
    }

    private AboutCourse saveAboutCourse(Session session, String name) {
        var aboutCourse = AboutCourse.builder()
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
                .start(LocalDate.of(2022, 10, 10))
                .finish(LocalDate.of(2023, 10, 10))
                .build();
    }

    public Student buildStudent() {
        return Student.builder()
                .personalInfo(PersonalInfo.builder()
                        .firstname("Антуан")
                        .lastname("Гризман")
                        .patronymic("Артурович")
                        .email("antuan@gmail.com")
                        .birthdate(LocalDate.of(1990, 9, 16))
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
                        .birthdate(LocalDate.of(1975, 9, 16))
                        .build())
                .profession("Веб-дизайнер")
                .build();
    };
}
