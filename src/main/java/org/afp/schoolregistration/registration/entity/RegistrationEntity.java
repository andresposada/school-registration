package org.afp.schoolregistration.registration.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.afp.schoolregistration.common.domain.GeneralEntity;
import org.afp.schoolregistration.course.entity.CourseEntity;
import org.afp.schoolregistration.student.entity.StudentEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "registrations")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class RegistrationEntity extends GeneralEntity {

  @ManyToOne
  @JoinColumn(name="student_id", nullable=false)
  StudentEntity student;

  @ManyToOne
  @JoinColumn(name="course_id", nullable=false)
  CourseEntity course;

}
