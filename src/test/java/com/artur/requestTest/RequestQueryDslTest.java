package com.artur.requestTest;

import com.artur.annotation.IT;
import com.artur.dto.TeacherFilter;
import com.artur.database.entity.Student;
import com.artur.request.RequestQueryDsl;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class RequestQueryDslTest {
    private final EntityManager entityManager;
    RequestQueryDsl queryDsl = RequestQueryDsl.getInstance();

    @BeforeEach
    void removeAndSaveData() {
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);
    }

    @Test
    void findAllStudentByCourseName() {
        var aboutCourse = UtilSave.getAboutCourses().get(0);

        var students = queryDsl.findAllStudentByCourseName(entityManager, aboutCourse);
        var actualResult = students.stream().map(Student::getId).collect(toList());

        assertThat(actualResult).contains(UtilSave.idStudent());
    }

    @Test
    void findCountStudentByTeacherNameAndSurname() {
        var studentsCount = queryDsl.findCountStudentByTeacherNameAndSurname(entityManager, TeacherFilter.builder().firstname("Gleb").lastname("Matveenka").build());

        assertThat(studentsCount).isEqualTo(8L);
    }

    @Test
    void findCourseNamesWithOrderedByAvgAgeStudents() {
        var tuples = queryDsl.findCourseNamesWithOrderedByAvgAgeStudents(entityManager);
        var aboutCourses = tuples.stream()
                .map(it -> it.get(0, String.class)).collect(toList());

        assertThat(aboutCourses).contains("Web-developer", "QA-developer", "Frontend-developer on Python");

        var avgAge = tuples.stream()
                .map(it -> it.get(1, Double.class))
                .map(a -> LocalDate.now().getYear() - a.intValue())
                .collect(toList());

        assertThat(avgAge).contains(37, 34, 26);;
    }
}
