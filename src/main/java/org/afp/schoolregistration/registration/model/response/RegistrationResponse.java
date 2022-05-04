package org.afp.schoolregistration.registration.model.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationResponse {
  UUID id;
  String studentId;
  String courseName;
}
