package com.artur.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Rating implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", table = "rating")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", table = "rating")
    private Student student;

    private Short rating;

    public void setStudent(Student student) {
        this.student = student;
        this.student.getRatings().add(this);
    }

    public void setCourse(Course course) {
        this.course = course;
        this.course.getRatings().add(this);
    }
}
