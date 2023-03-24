package com.artur.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "ratings")
@Builder
@Entity
public class Course implements BaseEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "about_course_id")
    private AboutCourse aboutCourse;

    private LocalDate start;
    private LocalDate finish;

    @Builder.Default
    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private List<Rating> ratings = new ArrayList<>();
}
