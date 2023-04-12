CREATE TABLE IF NOT EXISTS teacher
(
    id SERIAL PRIMARY KEY ,
    firstname VARCHAR (128),
    lastname VARCHAR (128),
    patronymic VARCHAR (128),
    birthdate DATE,
    email VARCHAR (128) UNIQUE ,
    profession VARCHAR (128)
);

CREATE TABLE IF NOT EXISTS about_course
(
    id SERIAL PRIMARY KEY ,
    teacher_id INT REFERENCES teacher(id),
    name VARCHAR (128) UNIQUE NOT NULL,
    cost_in_rubles INT ,
    max_student_number INT
);

CREATE TABLE IF NOT EXISTS course
(
    id BIGSERIAL PRIMARY KEY ,
    about_course_id INT REFERENCES about_course(id),
    start DATE UNIQUE ,
    finish DATE
);

CREATE TABLE IF NOT EXISTS student
(
    id BIGSERIAL PRIMARY KEY ,
    firstname VARCHAR (32),
    lastname VARCHAR (32),
    patronymic VARCHAR (64),
    birthdate DATE,
    email VARCHAR (128) UNIQUE
);

CREATE TABLE IF NOT EXISTS rating
(
    id BIGSERIAL PRIMARY KEY ,
    rating SMALLINT,
    student_id BIGINT NOT NULL REFERENCES student(id),
    course_id BIGINT NOT NULL REFERENCES course(id),
    UNIQUE (student_id, course_id)
);

