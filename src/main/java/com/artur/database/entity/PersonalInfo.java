package com.artur.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class PersonalInfo {

    private String firstname;
    private String lastname;
    private String patronymic;
    private LocalDate birthdate;
    @Column(unique = true)
    private String email;

}
