package com.artur.util;

import com.artur.entity.*;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class UtilSave {
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
    }
}
