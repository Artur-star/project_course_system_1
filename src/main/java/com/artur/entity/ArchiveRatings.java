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
public class ArchiveRatings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", table = "archive_ratings")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id", table = "archive_ratings")
    private Student student;

    private Short rating;

    public void setStudent(Student student) {
        this.student = student;
        this.student.getArchiveRatings().add(this);
    }

    public void setCourse(Course course) {
        this.course = course;
        this.course.getArchiveRatings().add(this);
    }
}
