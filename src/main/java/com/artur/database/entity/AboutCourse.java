
package com.artur.database.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
@EqualsAndHashCode(of = {"name", "costInRubles", "maxStudentsNumber"})
@ToString(exclude = {"teacher"})
public class AboutCourse implements BaseEntity<Integer> {
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
