package org.afp.schoolregistration.registration.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.afp.schoolregistration.common.exceptions.ObjectNotFoundException;
import org.afp.schoolregistration.course.domain.CourseMapper;
import org.afp.schoolregistration.course.repository.CourseMySQLJPARepository;
import org.afp.schoolregistration.registration.domain.Registration;
import org.afp.schoolregistration.registration.domain.RegistrationMapper;
import org.afp.schoolregistration.registration.model.request.CreateRegistrationRequest;
import org.afp.schoolregistration.registration.repository.RegistrationMySQLJPARepository;
import org.afp.schoolregistration.registration.service.RegistrationUseCase;
import org.afp.schoolregistration.student.domain.StudentMapper;
import org.afp.schoolregistration.student.repository.StudentMySQLJPARepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RegistrationService implements RegistrationUseCase {

  final StudentMySQLJPARepository studentMySQLJPARepository;
  final StudentMapper studentMapper;
  final CourseMySQLJPARepository courseMySQLJPARepository;
  final CourseMapper courseMapper;

  final RegistrationMapper registrationMapper;

  final RegistrationMySQLJPARepository registrationMySQLJPARepository;

  @Override
  public Mono<Registration> addRegistration(CreateRegistrationRequest createRegistrationRequest) {
    var course = courseMySQLJPARepository.findByCourseCode(createRegistrationRequest.getCourseCode())
            .map(courseMapper::courseEntityToCourse)
            .orElseThrow(() -> new ObjectNotFoundException(format("Course with code {0} was not found",
                    createRegistrationRequest.getCourseCode())));
    var registrations = registrationMySQLJPARepository.findByCourse_id(course.getId()).stream()
            .map(registrationMapper::registrationEntityToRegistration).collect(Collectors.toSet());
    course.setStudents(registrations);
    course.validateMaxAllowedStudents();

    var student =
            studentMySQLJPARepository.findByStudentId(createRegistrationRequest.getStudentId())
                    .map(studentMapper::studentEntityToStudent)
                    .orElseThrow(() -> new ObjectNotFoundException(
                            format("Student with student id {0} was not found",
                                    createRegistrationRequest.getStudentId())));

    student.setCourses(registrations.stream()
            .filter(reg -> reg.getStudent().getStudentId().equals(student.getStudentId()))
            .collect(Collectors.toSet()));
    student.validateCourseInscription(course.getCourseCode());
    student.validateMaxAllowedCourses();

    var registration = new Registration(student,course);
    return Mono.just(registrationMapper.registrationToRegistrationEntity(registration))
            .flatMap(entity -> Mono.just(registrationMySQLJPARepository.save(entity)))
            .map(registrationMapper::registrationEntityToRegistration);
  }

  @Override
  public Map<String, List<Registration>> getStudentsByCourse(String courseCode) {
    courseMySQLJPARepository.findByCourseCode(courseCode)
            .orElseThrow(() ->
                    new ObjectNotFoundException(format("Course with code {0} was not found", courseCode)));
    return registrationMySQLJPARepository.findByCourse_courseCode(courseCode).stream()
            .map(registrationMapper::registrationEntityToRegistration)
            .collect(Collectors.groupingBy(reg -> reg.getCourse().getCourseName()));
  }

  @Override
  public Map<String, List<Registration>> getCourseByStudent(String studentId) {
    studentMySQLJPARepository.findByStudentId(studentId)
            .orElseThrow(() -> new ObjectNotFoundException(format("Student with student id {0} was not found", studentId)));
    return registrationMySQLJPARepository.findByStudent_studentId(studentId)
            .stream()
            .map(registrationMapper::registrationEntityToRegistration)
            .collect(Collectors.groupingBy(reg -> reg.getStudent().getStudentId()));
  }

}
