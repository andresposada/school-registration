package org.afp.schoolregistration.registration.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request object to add a registration (register a student into a particular course)")
public class CreateRegistrationRequest {
  @NotBlank
  @Schema(description = "Student's identification")
  String studentId;
  @NotBlank
  @Schema(description = "Course's code")
  String courseCode;
}
