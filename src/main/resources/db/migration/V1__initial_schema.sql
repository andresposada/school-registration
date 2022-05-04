
CREATE TABLE students (
  id CHAR(36) NOT NULL,
  first_name VARCHAR(20) NOT NULL,
  last_name VARCHAR(30) NOT NULL,
  student_id VARCHAR(20) NOT NULL,
  email_address VARCHAR(50) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  updated_at TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY ( id )
);

CREATE INDEX students_student_id_idx ON students(student_id);

CREATE TABLE courses (
  id CHAR(36) NOT NULL,
  course_name VARCHAR(50) NOT NULL,
  course_code VARCHAR(10) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  updated_at TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY ( id )
);

CREATE INDEX courses_course_code_idx on courses(course_code);

CREATE TABLE registrations (
  id CHAR(36) NOT NULL,
  student_id VARCHAR(36) NOT NULL,
  course_id VARCHAR(36) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  updated_at TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY ( id ),
  KEY fk_student_id (student_id),
  KEY fk_course_id (course_id),
  CONSTRAINT fk_student_id FOREIGN KEY (student_id) REFERENCES students (id),
  CONSTRAINT fk_course_id FOREIGN KEY (course_id) REFERENCES courses (id)
);