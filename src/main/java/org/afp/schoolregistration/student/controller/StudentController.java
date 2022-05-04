package org.afp.schoolregistration.student.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.afp.schoolregistration.common.domain.response.ErrorResponse;
import org.afp.schoolregistration.student.domain.StudentMapper;
import org.afp.schoolregistration.student.model.request.CreateStudentRequest;
import org.afp.schoolregistration.student.model.request.UpdateStudentRequest;
import org.afp.schoolregistration.student.model.response.StudentFullDataResponse;
import org.afp.schoolregistration.student.model.response.StudentResponse;
import org.afp.schoolregistration.student.service.CRUDStudentUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v1/students")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentController {

  final CRUDStudentUseCase crudStudentUseCase;

  final StudentMapper studentMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Creates a student into the data storage")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "201",
                          description = "When a student is created correctly",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = StudentResponse.class))),
                  @ApiResponse(
                          responseCode = "412",
                          description = "When mandatory fields are not populated correctly",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = ErrorResponse.class)))
          })
  public Mono<ResponseEntity<StudentResponse>> createStudent(@RequestBody @Valid CreateStudentRequest createStudentRequest) {
    return crudStudentUseCase.createStudent(createStudentRequest)
            .map(studentMapper::studentToStudentResponse)
            .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
  }

  @PutMapping("/{id}")
  @ResponseStatus(code = HttpStatus.ACCEPTED)
  @Operation(summary = "Updates an existing student")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "When a student has been updated",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = StudentResponse.class))),
                  @ApiResponse(
                          responseCode = "404",
                          description = "When the student was not found by its id",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = ErrorResponse.class)))
          })
  public Mono<ResponseEntity<StudentResponse>> updateStudent(@PathVariable("id") UUID id,
          @RequestBody @Valid UpdateStudentRequest updateStudentRequest) {
    return crudStudentUseCase.updateStudent(updateStudentRequest, id)
            .map(studentMapper::studentToStudentResponse)
            .map(response -> ResponseEntity.status(HttpStatus.ACCEPTED).body(response));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deletes a student")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "204",
                          description = "When a student has been deleted"
                  ),
                  @ApiResponse(
                          responseCode = "406",
                          description = "When the student cannot be deleted from DB (dependency)",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = ErrorResponse.class)))
          })
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public Mono<Void> deleteStudent(@PathVariable("id") UUID id) {
    return crudStudentUseCase.deleteStudent(id);
  }

  @GetMapping("/{studentId}")
  @ResponseStatus(code = HttpStatus.OK)
  @Operation(summary = "Gets a student by its student id")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "When a student has been founded",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = StudentFullDataResponse.class))),
                  @ApiResponse(
                          responseCode = "404",
                          description = "When the student was not found by its id",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = ErrorResponse.class)))
          })
  public Mono<ResponseEntity<StudentFullDataResponse>> getStudentByStudentId(@PathVariable("studentId") String studentId) {
    return crudStudentUseCase.getStudentByStudentId(studentId)
            .map(studentMapper::studentToStudentFullDataResponse)
            .map(response -> ResponseEntity.status(HttpStatus.OK).body(response));
  }


}
