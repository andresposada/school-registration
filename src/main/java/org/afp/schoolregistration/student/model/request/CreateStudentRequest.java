package org.afp.schoolregistration.student.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Create student request data")
public class CreateStudentRequest {
  @NotBlank
  @Schema(description = "Student's first name")
  String firstName;

  @NotBlank
  @Schema(description = "Student's last name")
  String lastName;

  @NotBlank
  @Schema(description = "Student's id")
  String studentId;

  @NotNull
  @Schema(description = "Student's email address")
  String emailAddress;
}
