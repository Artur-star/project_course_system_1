package com.artur.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = "aboutCourse")
@Builder
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private PersonalInfo personalInfo;
    private String profession;

    @OneToOne(mappedBy = "teacher")
    private AboutCourse aboutCourse;

    public void setAboutCourse(AboutCourse aboutCourse) {
        this.aboutCourse = aboutCourse;
        aboutCourse.setTeacher(this);
    }
}
