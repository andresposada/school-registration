package org.afp.schoolregistration.student.domain;

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
@EqualsAndHashCode(callSuper = true)
public class Student extends Domain {
  static final int MAX_ALLOWED_COURSES = 5;
  String firstName;
  String lastName;
  String studentId;
  String emailAddress;
  Set<Registration> courses;


  public void validateMaxAllowedCourses() {
    if (getCourses().size() > MAX_ALLOWED_COURSES) {
      throw new BusinessValidationException(format(
              "Student {0} has reached maximum allowed registered courses. Max allowed courses: {1} - Registered courses: {2}",
              this.getStudentId(), MAX_ALLOWED_COURSES, this.getCourses().size()));
    }
  }

  public void validateCourseInscription(String courseCode) {
    this.getCourses().stream()
            .filter(registration -> registration.getCourse().getCourseCode().equals(courseCode))
            .findAny()
            .ifPresent(r -> {
              throw new BusinessValidationException(format("Student {0} is already registered in the course {1}",
                      this.getStudentId(), courseCode));
            });
  }
}
