package org.afp.schoolregistration.student.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.afp.schoolregistration.common.exceptions.DuplicatedEntryException;
import org.afp.schoolregistration.common.exceptions.ObjectNotFoundException;
import org.afp.schoolregistration.student.domain.Student;
import org.afp.schoolregistration.student.domain.StudentMapper;
import org.afp.schoolregistration.student.model.request.CreateStudentRequest;
import org.afp.schoolregistration.student.model.request.UpdateStudentRequest;
import org.afp.schoolregistration.student.repository.StudentMySQLJPARepository;
import org.afp.schoolregistration.student.service.CRUDStudentUseCase;
import org.afp.schoolregistration.student.service.StudentWithoutCourseUseCase;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class StudentService implements CRUDStudentUseCase, StudentWithoutCourseUseCase {

  final StudentMapper studentMapper;
  final StudentMySQLJPARepository studentMySQLJPARepository;

  @Override
  public Mono<Student> createStudent(CreateStudentRequest createStudentRequest) {
    return Mono.just(createStudentRequest)
            .flatMap(request -> Mono.just(studentMySQLJPARepository.findByStudentId(createStudentRequest.getStudentId())))
            .flatMap(optEntity -> {
              if (optEntity.isPresent()) {
                return Mono.error(new DuplicatedEntryException(
                        format("Student with id {0} already registered", createStudentRequest.getStudentId())));
              }
              return Mono.just(studentMapper.studentToStudentEntity(studentMapper.createStudentRequestToStudent(createStudentRequest)));
            })
            .flatMap(entity -> Mono.just(studentMySQLJPARepository.save(entity)))
            .map(studentMapper::studentEntityToStudent);
  }

  @Override
  public Mono<Student> updateStudent(UpdateStudentRequest updateStudentRequest, UUID id) {
    return Mono.just(id)
            .flatMap(studentId -> Mono.just(studentMySQLJPARepository.findById(studentId)))
            .flatMap(optEntity -> optEntity.map(entity -> {
              studentMapper.updateStudentEntity(entity, studentMapper.updateStudentRequestToStudent(updateStudentRequest));
              return Mono.just(entity);
            }).orElseThrow(() -> new ObjectNotFoundException(format("Student with primary key {0} was not found", id))))
            .flatMap(entity -> Mono.just(studentMySQLJPARepository.save(entity)))
            .map(studentMapper::studentEntityToStudent);
  }

  @Override
  public Mono<Void> deleteStudent(UUID id) {
    return Mono.just(id)
            .flatMap(studentId -> Mono.just(studentMySQLJPARepository.findById(id)))
            .flatMap(optEntity -> optEntity.map(entity -> {
              studentMySQLJPARepository.delete(entity);
              return Mono.empty();
            }).orElse(Mono.error(new ObjectNotFoundException(format("Student with primary key {0} was not found", id)))))
            .then();
  }

  @Override
  @Transactional
  public Mono<Student> getStudentByStudentId(String studentId) {
    return Mono.just(studentId)
            .flatMap(stId -> Mono.just(studentMySQLJPARepository.findByStudentId(stId)))
            .flatMap(optEntity -> optEntity.map(entity -> Mono.just(studentMapper.studentEntityToStudent(entity)))
                    .orElseThrow(() -> new ObjectNotFoundException(format("Student with student id {0} was not found", studentId))));
  }

  @Override
  public Flux<Student> studentsWithoutCourse() {
    return Flux.fromIterable(studentMySQLJPARepository.findStudentsWithoutCourse().stream()
            .map(studentMapper::studentEntityToStudent)
            .collect(Collectors.toList()));
  }
}
