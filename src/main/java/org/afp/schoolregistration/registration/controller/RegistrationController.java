package org.afp.schoolregistration.registration.controller;

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
import org.afp.schoolregistration.course.model.response.CourseResponse;
import org.afp.schoolregistration.course.service.EmptyCourseUseCase;
import org.afp.schoolregistration.registration.domain.RegistrationMapper;
import org.afp.schoolregistration.registration.model.request.CreateRegistrationRequest;
import org.afp.schoolregistration.registration.model.response.RegistrationByCourseResponse;
import org.afp.schoolregistration.registration.model.response.RegistrationByStudentResponse;
import org.afp.schoolregistration.registration.model.response.RegistrationResponse;
import org.afp.schoolregistration.registration.service.RegistrationUseCase;
import org.afp.schoolregistration.student.domain.StudentMapper;
import org.afp.schoolregistration.student.model.response.StudentResponse;
import org.afp.schoolregistration.student.service.StudentWithoutCourseUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/registrations")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RegistrationController {

  final RegistrationUseCase registrationUseCase;

  final EmptyCourseUseCase emptyCourseUseCase;

  final CourseMapper courseMapper;

  final StudentWithoutCourseUseCase studentWithoutCourseUseCase;

  final StudentMapper studentMapper;

  final RegistrationMapper registrationMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Creates a registration in the system (Add a student to a particular course)")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "201",
                          description = "When a registration has been filed correctly",
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
  public Mono<ResponseEntity<RegistrationResponse>> addRegistration(@Valid @RequestBody CreateRegistrationRequest createRegistrationRequest) {
    return registrationUseCase.addRegistration(createRegistrationRequest)
            .map(registrationMapper::registrationToRegistrationResponse)
            .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
  }

  @GetMapping("/courses/{courseCode}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get students registered in a particular course")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "When retrieving students from the queried course",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = RegistrationByCourseResponse.class))),
                  @ApiResponse(
                          responseCode = "404",
                          description = "When the queried course does not exits",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = ErrorResponse.class)))
          })
  public Flux<RegistrationByCourseResponse> getRegistrationByCourse(@PathVariable("courseCode") String courseCode) {
    return Flux.fromIterable(registrationUseCase.getStudentsByCourse(courseCode)
            .entrySet().stream().map(entry -> {
              var regStudentList = entry.getValue().stream().map(reg ->
                      RegistrationByCourseResponse.RegistrationStudentResponse.builder()
                              .studentId(reg.getStudent().getStudentId())
                              .studentName(reg.getStudent().getFirstName().concat(" ").concat(reg.getStudent().getLastName()))
                              .build())
                      .collect(Collectors.toList());
              return RegistrationByCourseResponse.builder().courseName(entry.getKey()).students(regStudentList).build();
            })
            .collect(Collectors.toList()));
  }

  @GetMapping("/students/{studentId}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get courses registered to a particular student")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "When retrieving courses registered to the queried student",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = RegistrationByStudentResponse.class))),
                  @ApiResponse(
                          responseCode = "404",
                          description = "When the queried student does not exits",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = ErrorResponse.class)))
          })
  public Flux<RegistrationByStudentResponse> getRegistrationByStudent(@PathVariable("studentId") String studentId) {
    return Flux.fromIterable(
      registrationUseCase.getCourseByStudent(studentId).entrySet().stream()
              .map(entry -> {
                var courseList = entry.getValue().stream().map(reg ->
                                RegistrationByStudentResponse.RegistrationCourseResponse.builder()
                                        .courseCode(reg.getCourse().getCourseCode())
                                        .courseName(reg.getCourse().getCourseName())
                                        .build()
                        ).collect(Collectors.toList());
                return RegistrationByStudentResponse.builder()
                        .studentId(entry.getKey())
                        .courses(courseList)
                        .build();
              }).collect(Collectors.toList())
    );
  }

  @GetMapping("/courses/empty")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get courses without students")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "When retrieving empty courses",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = CourseResponse.class)))
          })
  public Flux<CourseResponse> getCoursesWithoutStudents() {
    return emptyCourseUseCase.getCoursesWithoutStudents()
            .map(courseMapper::courseToCourseResponse);
  }

  @Operation(summary = "Get courses without students")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "When retrieving students without any registered course",
                          content =
                          @Content(
                                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                                  schema = @Schema(implementation = StudentResponse.class)))
          })
  @GetMapping("/students/empty")
  @ResponseStatus(HttpStatus.OK)
  public Flux<StudentResponse> getStudentsWithoutCourse() {
    return studentWithoutCourseUseCase.studentsWithoutCourse()
            .map(studentMapper::studentToStudentResponse);
  }
}
