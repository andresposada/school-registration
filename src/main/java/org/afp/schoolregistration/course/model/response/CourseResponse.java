package org.afp.schoolregistration.course.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Course response object")
public class CourseResponse {
  @Schema(description = "Course's primary key")
  UUID id;
  @Schema(description = "Course's name")
  String courseName;
  @Schema(description = "Course's code")
  String courseCode;
}
