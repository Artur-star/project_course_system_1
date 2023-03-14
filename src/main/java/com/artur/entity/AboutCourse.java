package com.artur.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class AboutCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "teacher_id", table = "about_course")
    private Teacher teacher;
    private String name;
    private Integer costInRubles;
    private Integer maxStudentsNumber;

    @Builder.Default
    @OneToMany(mappedBy = "aboutCourse")
    private List<Course> courses = new ArrayList<>();

    public void addAboutCourse(Course course) {
        courses.add(course);
        course.setAboutCourse(this);
    }
}
