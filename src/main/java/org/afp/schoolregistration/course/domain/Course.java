package org.afp.schoolregistration.course.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.afp.schoolregistration.common.domain.Domain;
import org.afp.schoolregistration.common.exceptions.BusinessValidationException;
import org.afp.schoolregistration.registration.domain.Registration;

import java.util.Set;

import static java.text.MessageFormat.format;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class Course extends Domain {
  final static int MAX_ALLOWED_STUDENTS = 50;
  String courseName;
  String courseCode;
  Set<Registration> students;

  public void validateMaxAllowedStudents() {
    if (getStudents().size() > MAX_ALLOWED_STUDENTS) {
      throw new BusinessValidationException(format("Course {0} has reached max allowed students, cannot add more students. Max allowed students per course {1}",
              this.getCourseName(), MAX_ALLOWED_STUDENTS));
    }
  }
}
