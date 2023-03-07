package com.artur.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    @OneToMany(mappedBy = "teacher")
    private List<AboutCourse> aboutCourses = new ArrayList<>();

    public void setAboutCourse(AboutCourse aboutCourse) {
        aboutCourses.add(aboutCourse);
        aboutCourse.setTeacher(this);
    }
}
