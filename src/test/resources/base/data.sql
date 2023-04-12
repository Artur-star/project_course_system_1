INSERT INTO teacher (firstname, lastname, email, profession)
VALUES ('Gleb', 'Matveenka', 'asd3@fer.com', 'Веб-дизайнер'),
       ('Vlad', 'Vilnus', 'dasdqw1@fer.com', 'QA-инженер');

INSERT INTO about_course (name, teacher_id)
VALUES ('Web-developer', (SELECT id FROM teacher WHERE email = 'asd3@fer.com')),
       ('Frontend-developer on Python', (SELECT id FROM teacher WHERE email = 'dasdqw1@fer.com')),
       ('QA-developer', (SELECT id FROM teacher WHERE email = 'dasdqw1@fer.com'));

INSERT INTO course (start, finish, about_course_id)
VALUES ('2021-2-10', '2022-2-10', (SELECT id FROM about_course WHERE name = 'Web-developer')),
       ('2021-5-10', '2022-5-10', (SELECT id FROM about_course WHERE name = 'Web-developer')),
       ('2022-3-10', '2023-3-10', (SELECT id FROM about_course WHERE name = 'Frontend-developer on Python')),
       ('2022-6-10', '2023-6-10', (SELECT id FROM about_course WHERE name = 'QA-developer'));

INSERT INTO student (firstname, lastname, email, birthdate)
VALUES ('Anton', 'Gyev', 'aaa@asd.ru','1974-11-11'),
       ('Andrey', 'Robich', 'aaa1@asd.ru','1984-11-11'),
       ('Alex', 'Keln', 'aaa2@asd.ru','1994-11-11'),
       ('Alfred', 'Gaag', 'aaa3@asd.ru','1995-11-11'),
       ('Albert', 'Dobrev', 'aaa4@asd.ru','1999-11-11');

INSERT INTO rating (course_id, student_id, rating)
VALUES ((SELECT id FROM course WHERE start = '2021-2-10'), (SELECT id FROM student WHERE email = 'aaa@asd.ru'), 5),
       ((SELECT id FROM course WHERE start = '2021-2-10'), (SELECT id FROM student WHERE email = 'aaa1@asd.ru'), 5),
       ((SELECT id FROM course WHERE start = '2021-2-10'), (SELECT id FROM student WHERE email = 'aaa2@asd.ru'), 4),
       ((SELECT id FROM course WHERE start = '2021-5-10'), (SELECT id FROM student WHERE email = 'aaa4@asd.ru'), 4),
       ((SELECT id FROM course WHERE start = '2021-5-10'), (SELECT id FROM student WHERE email = 'aaa@asd.ru'), 5),
       ((SELECT id FROM course WHERE start = '2021-5-10'), (SELECT id FROM student WHERE email = 'aaa2@asd.ru'), 4),
       ((SELECT id FROM course WHERE start = '2022-3-10'), (SELECT id FROM student WHERE email = 'aaa3@asd.ru'), 5),
       ((SELECT id FROM course WHERE start = '2022-3-10'), (SELECT id FROM student WHERE email = 'aaa4@asd.ru'), 4),
       ((SELECT id FROM course WHERE start = '2022-6-10'), (SELECT id FROM student WHERE email = 'aaa1@asd.ru'), 5),
       ((SELECT id FROM course WHERE start = '2022-6-10'), (SELECT id FROM student WHERE email = 'aaa3@asd.ru'), 3);

