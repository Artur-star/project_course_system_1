package com.artur.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CourseFilter {
    String name;
}
