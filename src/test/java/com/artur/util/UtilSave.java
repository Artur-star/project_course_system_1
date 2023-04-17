package com.artur.util;

import com.artur.database.entity.Teacher;
import com.artur.database.entity.AboutCourse;
import com.artur.database.entity.Course;
import com.artur.database.entity.Student;
import com.artur.database.entity.Rating;
import com.artur.database.entity.PersonalInfo;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.*;

@UtilityClass
@Data
public class UtilSave {
    @Getter
    private final List<Teacher> teachers = new ArrayList<>();
    @Getter
    private final List<AboutCourse> aboutCourses = new ArrayList<>();
    @Getter
    private final List<Course> courses = new ArrayList<>();
    @Getter
    private final List<Student> students = new ArrayList<>();
    @Getter
    private final List<Rating> ratings = new ArrayList<>();

    public void importData(EntityManager entityManager) {

        var webGlebMatveenka = saveTeacher(entityManager, "Gleb", "Matveenka", "asd2@fer.com", "Веб-дизайнер");
        var qaVladVilnus = saveTeacher(entityManager, "Vlad", "Vilnus", "dasdqw@fer.com", "QA-инженер");


        var webDev = saveAboutCourse(entityManager, "Web-developer", webGlebMatveenka);
        var frontendDev = saveAboutCourse(entityManager, "Frontend-developer on Python", webGlebMatveenka);
        var qaDev = saveAboutCourse(entityManager, "QA-developer", qaVladVilnus);



        var course = saveCourse(entityManager, of(2021, 2, 10), of(2022, 2, 10), webDev);
        var course1 = saveCourse(entityManager, of(2021, 5, 20), of(2022, 5, 20), webDev);
        var course2 = saveCourse(entityManager, of(2022, 3, 10), of(2023, 3, 10), frontendDev);
        var course3 = saveCourse(entityManager, of(2022, 6, 10), of(2023, 6, 10), qaDev);


        var antonGyev = saveStudent(entityManager, "Anton", "Gyev", "aaa@asd.ru", of(1974, 11, 11));
        var andreyRobich = saveStudent(entityManager, "Andrey", "Robich", "aaa1@asd.ru",  of(1984, 11, 11));
        var alexKeln = saveStudent(entityManager, "Alex", "Keln", "aaa2@asd.ru",  of(1994, 11, 11));
        var alfredGaag = saveStudent(entityManager, "Alfred", "Gaag", "aaa3@asd.ru",  of(1995, 11, 11));
        var albertDobrev = saveStudent(entityManager, "Albert", "Dobrev", "aaa4@asd.ru",  of(1999, 11, 11));


        var rating = saveRating(entityManager, course, antonGyev, (short) 5);
        var rating1 = saveRating(entityManager, course, andreyRobich, (short) 5);
        var rating2 = saveRating(entityManager, course, alexKeln, (short) 4);
        var rating3 = saveRating(entityManager, course1, albertDobrev, (short) 4);
        var rating4 = saveRating(entityManager, course1, antonGyev, (short) 5);
        var rating5 = saveRating(entityManager, course1, alexKeln, (short) 4);
        var rating6 = saveRating(entityManager, course2, alfredGaag, (short) 5);
        var rating7 = saveRating(entityManager, course2, albertDobrev, (short) 4);
        var rating8 = saveRating(entityManager, course3, andreyRobich, (short) 5);
        var rating9 = saveRating(entityManager, course3, alfredGaag, (short) 3);

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

    private Rating saveRating(EntityManager entityManager, Course course, Student student, Short estimate) {
        var rating = Rating.builder()
                .course(course)
                .student(student)
                .rating(estimate)
                .build();
        entityManager.persist(rating);
        return rating;
    }

    private Course saveCourse(EntityManager entityManager , LocalDate start, LocalDate finish, AboutCourse aboutCourse) {
        var course = Course.builder()
                .aboutCourse(aboutCourse)
                .start(start)
                .finish(finish)
                .build();
        entityManager.persist(course);
        return course;
    }

    private Teacher saveTeacher(EntityManager entityManager, String firstname, String lastname, String email, String profession) {
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

        entityManager.persist(teacher);
        return teacher;
    }

    private Student saveStudent(EntityManager entityManager, String firstname, String lastname, String email, LocalDate birthday) {
        var student = Student.builder()
                .personalInfo(PersonalInfo.builder()
                        .firstname(firstname)
                        .lastname(lastname)
                        .patronymic("Артурович")
                        .email(email)
                        .birthdate(birthday)
                        .build())
                .build();
        entityManager.persist(student);
        return student;
    }

    private AboutCourse saveAboutCourse(EntityManager entityManager, String name , Teacher teacher) {
        var aboutCourse = AboutCourse.builder()
                .teacher(teacher)
                .name(name)
                .costInRubles(80000)
                .maxStudentsNumber(5)
                .build();
        entityManager.persist(aboutCourse);
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
}
