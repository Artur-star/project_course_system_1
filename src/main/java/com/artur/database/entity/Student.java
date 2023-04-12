package com.artur.database.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "ratings")
@Entity
public class Student implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PersonalInfo personalInfo;

    @Builder.Default
    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<Rating> ratings = new ArrayList<>();
}
