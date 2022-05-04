package org.afp.schoolregistration.registration.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.afp.schoolregistration.common.domain.Domain;
import org.afp.schoolregistration.course.domain.Course;
import org.afp.schoolregistration.student.domain.Student;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Registration extends Domain {

  Student student;
  Course course;

}
