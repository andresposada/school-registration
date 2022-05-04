package org.afp.schoolregistration.registration.model.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RegistrationByCourseResponse {
  String courseName;
  List<RegistrationStudentResponse> students;

  @Data
  @FieldDefaults(level = AccessLevel.PRIVATE)
  @Builder
  public static class RegistrationStudentResponse {
    String studentName;
    String studentId;
  }

}
