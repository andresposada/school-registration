package org.afp.schoolregistration.registration.model.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RegistrationByStudentResponse {
  String studentId;
  List<RegistrationCourseResponse> courses;

  @Data
  @FieldDefaults(level = AccessLevel.PRIVATE)
  @Builder
  public static class RegistrationCourseResponse {
    String courseName;
    String courseCode;
  }

}
