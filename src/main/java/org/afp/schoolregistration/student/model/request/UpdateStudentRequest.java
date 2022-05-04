package org.afp.schoolregistration.student.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Update student request data")
public class UpdateStudentRequest {
  @Schema(description = "Student's first name")
  @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
  String firstName;

  @Schema(description = "Student's last name")
  @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
  String lastName;

  @Schema(description = "Student's id")
  @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
  String studentId;

  @Schema(description = "Student's email address")
  @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
  @Email
  String emailAddress;
}
