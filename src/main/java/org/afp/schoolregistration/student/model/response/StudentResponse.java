package org.afp.schoolregistration.student.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Student's response object")
public class StudentResponse {
  @Schema(description = "Student's primary key")
  UUID id;
  @Schema(description = "Student's id")
  String studentId;
}
