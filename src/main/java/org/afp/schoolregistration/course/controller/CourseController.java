package org.afp.schoolregistration.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.afp.schoolregistration.common.domain.response.ErrorResponse;
import org.afp.schoolregistration.course.domain.CourseMapper;
import org.afp.schoolregistration.course.model.request.CreateCourseRequest;
import org.afp.schoolregistration.course.model.request.UpdateCourseRequest;
import org.afp.schoolregistration.course.model.response.CourseResponse;
import org.afp.schoolregistration.course.service.CRUDCourseUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v1/courses")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseController {

  final CRUDCourseUseCase crudCourseUseCase;
  final CourseMapper courseMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Creates a course into the data storage")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "201",
                          description = "When a course is created correctly",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = CourseResponse.class))),
                  @ApiResponse(
                          responseCode = "412",
                          description = "When mandatory fields are not populated correctly",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = ErrorResponse.class)))
          })
  public Mono<ResponseEntity<CourseResponse>> createCourse(@Valid @RequestBody CreateCourseRequest createCourseRequest) {
    return crudCourseUseCase.createCourse(createCourseRequest)
            .map(courseMapper::courseToCourseResponse)
            .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @Operation(summary = "Updates an existing student")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "When a course has been updated",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = CourseResponse.class))),
                  @ApiResponse(
                          responseCode = "404",
                          description = "When the course was not found by its id",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = ErrorResponse.class)))
          })
  public Mono<ResponseEntity<CourseResponse>> updateCourse(@PathVariable("id") UUID id,
          @Valid @RequestBody UpdateCourseRequest updateCourseRequest) {
    return crudCourseUseCase.updateCourse(updateCourseRequest, id)
            .map(courseMapper::courseToCourseResponse)
            .map(response -> ResponseEntity.status(HttpStatus.ACCEPTED).body(response));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Deletes a course")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "204",
                          description = "When a course has been deleted"
                  ),
                  @ApiResponse(
                          responseCode = "406",
                          description = "When the course cannot be deleted from DB (dependency)",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = ErrorResponse.class)))
          })
  public Mono<Void> deleteCourse(@PathVariable("id") UUID id) {
    return crudCourseUseCase.deleteCourse(id);
  }

  @GetMapping("/{code}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Gets an course by code")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "When a course has been founded",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = CourseResponse.class))),
                  @ApiResponse(
                          responseCode = "404",
                          description = "When the course was not found by its code",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = ErrorResponse.class)))
          })
  public Mono<ResponseEntity<CourseResponse>> getCourseByCode(@PathVariable("code") String code) {
    return crudCourseUseCase.getCourseByCode(code)
            .map(courseMapper::courseToCourseResponse)
            .map(response -> ResponseEntity.status(HttpStatus.OK).body(response));

  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Gets all the courses registered in the DB")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "When the course list has been founded",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = CourseResponse.class)))
          })
  public Flux<CourseResponse> getCourses() {
    return crudCourseUseCase.getAllCourses().map(courseMapper::courseToCourseResponse);
  }
}
