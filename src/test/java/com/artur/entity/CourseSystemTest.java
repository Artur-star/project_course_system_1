//package com.artur.entity;
//
//import com.artur.config.ApplicationConfigurationTest;
//import com.artur.util.UtilDelete;
//import com.artur.util.UtilSave;
//import org.hibernate.SessionFactory;
//import org.junit.jupiter.api.*;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//import static org.assertj.core.api.Assertions.*;
//
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class CourseSystemTest {
//
//    SessionFactory sessionFactory;
//
//    @BeforeAll
//    void createAppContext() {
//        var context = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
//        sessionFactory = context.getBean("buildSessionFactory", SessionFactory.class);
//    }
//
//    @BeforeEach
//    void createData() {
//        UtilSave.importData(sessionFactory);
//    }
//
//    @AfterEach
//    void deleteData() {
//        UtilDelete.deleteData(sessionFactory);
//    }
//
//    @Test
//    void testSaveAndGetTeacher() {
//        try (var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            var aboutCourse = UtilSave.buildAboutCourse();
//            var expectedTeacher = UtilSave.buildTeacher();
//            expectedTeacher.setAboutCourse(aboutCourse);
//            session.save(aboutCourse);
//
//            var idTeacher = session.save(expectedTeacher);
//            session.evict(expectedTeacher);
//            var actualTeacher = session.get(Teacher.class, idTeacher);
//
//            assertThat(actualTeacher.getId()).isEqualTo(expectedTeacher.getId());
//            session.getTransaction().commit();
//        }
//    }
//
//    @Test
//    void testSaveAndGetAboutCourse() {
//        try (var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            var expectedAboutCourse = UtilSave.buildAboutCourse();
//            var teacher = UtilSave.buildTeacher();
//            teacher.setAboutCourse(expectedAboutCourse);
//            session.save(teacher);
//
//            var idAboutCourse = session.save(expectedAboutCourse);
//            session.evict(expectedAboutCourse);
//            var actualAboutCourse = session.get(AboutCourse.class, idAboutCourse);
//
//            assertThat(actualAboutCourse.getId()).isEqualTo(expectedAboutCourse.getId());
//            session.getTransaction().commit();
//        }
//    }
//
//    @Test
//    void testSaveAndGetRating() {
//        try (var session = sessionFactory.openSession()) {
//                session.beginTransaction();
//                var aboutCourse = UtilSave.buildAboutCourse();
//                var teacher = UtilSave.buildTeacher();
//                var course = UtilSave.buildCourse();
//                var student = UtilSave.buildStudent();
//                var expectedRating = UtilSave.buildRating();
//                teacher.setAboutCourse(aboutCourse);
//                aboutCourse.addAboutCourse(course);
//                expectedRating.setStudent(student);
//                expectedRating.setCourse(course);
//                session.save(teacher);
//                session.save(aboutCourse);
//                session.save(student);
//                session.save(course);
//
//                var idRating = session.save(expectedRating);
//                session.evict(expectedRating);
//                var actualRating = session.get(Rating.class, idRating);
//
//                assertThat(actualRating).isEqualTo(expectedRating);
//                session.getTransaction().commit();
//        }
//    }
//
//    @Test
//    void testSaveAndGetStudent() {
//        try (var session = sessionFactory.openSession()) {
//                session.beginTransaction();
//                var expectedStudent = UtilSave.buildStudent();
//
//                var studentId = session.save(expectedStudent);
//                session.evict(expectedStudent);
//                var actualStudent = session.get(Student.class, studentId);
//
//                assertThat(actualStudent.getId()).isEqualTo(expectedStudent.getId());
//                session.getTransaction().commit();
//        }
//    }
//
//    @Test
//    void testSaveAndGetCourse() {
//        try (var session = sessionFactory.openSession()) {
//                session.beginTransaction();
//                var expectedCourse = UtilSave.buildCourse();
//                var aboutCourse = UtilSave.buildAboutCourse();
//                var teacher = UtilSave.buildTeacher();
//                teacher.setAboutCourse(aboutCourse);
//                aboutCourse.addAboutCourse(expectedCourse);
//                session.save(teacher);
//                session.save(aboutCourse);
//
//                var courseId = session.save(expectedCourse);
//                session.evict(expectedCourse);
//                var actualCourse = session.get(Course.class, courseId);
//
//                assertThat(actualCourse.getId()).isEqualTo(expectedCourse.getId());
//                session.getTransaction().commit();
//        }
//    }
//}