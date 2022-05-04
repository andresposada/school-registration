package org.afp.schoolregistration.student.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.afp.schoolregistration.common.domain.GeneralEntity;
import org.afp.schoolregistration.registration.entity.RegistrationEntity;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "students")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentEntity extends GeneralEntity {

  @Column(name = "first_name", nullable = false)
  String firstName;

  @Column(name = "last_name", nullable = false)
  String lastName;

  @Column(name = "student_id", nullable = false)
  String studentId;

  @Column(name = "email_address", nullable = false)
  String emailAddress;

  @OneToMany(mappedBy="student")
  Set<RegistrationEntity> courses;

}
