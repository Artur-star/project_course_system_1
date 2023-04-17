package com.artur.requestTest;

import com.artur.annotation.IT;
import com.artur.database.entity.Student;
import com.artur.request.RequestCriteriaAPI;
import com.artur.util.UtilDelete;
import com.artur.util.UtilSave;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class RequestCriteriaApiTest {
    private final EntityManager entityManager;
    RequestCriteriaAPI criteriaAPI = RequestCriteriaAPI.getInstance();

    @BeforeEach
    void removeAndSaveData() {
        UtilDelete.deleteData(entityManager);
        UtilSave.importData(entityManager);
    }

    @Test
    void findAll() {
        var students = criteriaAPI.findAll(entityManager);

        var actualResult = students.stream().map(Student::getId).collect(toList());

        assertThat(actualResult).contains(1L);
    }

    @Test
    void findMaxCountStudentByFirstnameAndLastnameTeacher() {
        var tuples = criteriaAPI.findMaxCountStudentByFirstnameAndLastnameTeacher(entityManager, "Gleb", "Matveenka");

        var countStudent = tuples.stream().map(it -> it.get(0, Long.class)).collect(toList());
        assertThat(countStudent).contains(8L);

        var teacherFirstname = tuples.stream().map(it -> it.get(1, String.class)).collect(toList());
        assertThat(teacherFirstname).contains("Gleb");

        var teacherLastname = tuples.stream().map(it -> it.get(2, String.class)).collect(toList());
        assertThat(teacherLastname).contains("Matveenka");
    }
}
