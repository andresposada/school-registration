package org.afp.schoolregistration.course.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.afp.schoolregistration.common.domain.GeneralEntity;
import org.afp.schoolregistration.registration.entity.RegistrationEntity;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "courses")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class CourseEntity extends GeneralEntity {

  @Column(name = "course_name", nullable = false)
  String courseName;

  @Column(name = "course_code", nullable = false)
  String courseCode;

  @OneToMany(mappedBy = "course")
  Set<RegistrationEntity> students;

}
