package com.artur.database.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Embedded;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"personalInfo", "profession"})
//@ToString(exclude = "aboutCourses")
@Builder
@Entity
public class Teacher implements BaseEntity<Integer>{
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
