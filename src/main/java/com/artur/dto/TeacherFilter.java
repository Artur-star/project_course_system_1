package com.artur.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import org.springframework.transaction.annotation.Transactional;


@Value
@Builder
public class TeacherFilter {
    String firstname;
    String lastname;
}
