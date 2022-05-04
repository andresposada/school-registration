package org.afp.schoolregistration.course.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request object to create a course")
public class CreateCourseRequest {
  @NotBlank
  @Schema(description = "Course's name")
  String courseName;

  @NotBlank
  @Schema(description = "Course's code")
  String courseCode;
}
