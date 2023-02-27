package com.artur.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serial;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class CompositeKey implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer listCoursesId;
    private Long studentId;
}
