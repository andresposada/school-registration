package org.afp.schoolregistration.student.model.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentFullDataResponse {
  UUID id;
  String firstName;
  String lastName;
  String studentId;
  String emailAddress;
}
