//package com.artur.requestTest;
//
//import com.artur.database.entity.Student;
//import com.artur.request.RequestCriteriaAPI;
//import com.artur.util.HibernateTestUtil;
//import com.artur.util.UtilDelete;
//import com.artur.util.UtilSave;
//import lombok.Cleanup;
//import org.hibernate.SessionFactory;
//import org.junit.jupiter.api.*;
//
//import static java.util.stream.Collectors.toList;
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class RequestCriteriaApiTest {
//
//    SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
//    RequestCriteriaAPI criteriaAPI = RequestCriteriaAPI.getInstance();
//
//    @BeforeEach
//    void startEach() {
//        UtilSave.importData(sessionFactory);
//    }
//
//    @AfterEach
//    void endEach() {
//        UtilDelete.deleteData(sessionFactory);
//    }
//
//    @Test
//    void findAll() {
//        @Cleanup var session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var students = criteriaAPI.findAll(session);
//
//        var actualResult = students.stream().map(Student::getId).collect(toList());
//
//        assertThat(actualResult).contains(1L);
//
//        session.getTransaction().commit();
//    }
//
//    @Test
//    void findMaxCountStudentByFirstnameAndLastnameTeacher() {
//        @Cleanup var session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var tuples = criteriaAPI.findMaxCountStudentByFirstnameAndLastnameTeacher(session, "Gleb", "Matveenka");
//
//        var countStudent = tuples.stream().map(it -> it.get(0, Long.class)).collect(toList());
//        assertThat(countStudent).contains(8L);
//
//        var teacherFirstname = tuples.stream().map(it -> it.get(1, String.class)).collect(toList());
//        assertThat(teacherFirstname).contains("Gleb");
//
//        var teacherLastname = tuples.stream().map(it -> it.get(2, String.class)).collect(toList());
//        assertThat(teacherLastname).contains("Matveenka");
//
//        session.getTransaction().commit();
//
//    }
//}
