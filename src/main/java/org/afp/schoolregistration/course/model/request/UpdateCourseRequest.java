package org.afp.schoolregistration.course.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Pattern;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request object to update an existing course")
public class UpdateCourseRequest {

  @Schema(description = "Course's name")
  @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
  String courseName;

  @Schema(description = "Course's code")
  @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
  String courseCode;

}
