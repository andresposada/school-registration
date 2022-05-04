package org.afp.schoolregistration.student.service;

import org.afp.schoolregistration.student.domain.Student;
import org.afp.schoolregistration.student.model.request.CreateStudentRequest;
import org.afp.schoolregistration.student.model.request.UpdateStudentRequest;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CRUDStudentUseCase {

  Mono<Student> createStudent(CreateStudentRequest createStudentRequest);
  Mono<Student> updateStudent(UpdateStudentRequest updateStudentRequest, UUID id);

  Mono<Void> deleteStudent(UUID id);

  Mono<Student> getStudentByStudentId(String studentId);

}
