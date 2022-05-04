package org.afp.schoolregistration.school.service;

import org.afp.schoolregistration.common.exceptions.DuplicatedEntryException;
import org.afp.schoolregistration.common.exceptions.ObjectNotFoundException;
import org.afp.schoolregistration.student.domain.StudentMapper;
import org.afp.schoolregistration.student.repository.StudentMySQLJPARepository;
import org.afp.schoolregistration.student.service.impl.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.test.StepVerifier;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class StudentServiceTest {

  private StudentService studentService;

  @Mock
  private StudentMySQLJPARepository repository;

  private final StudentMapper studentMapper = Mappers.getMapper(StudentMapper.class);

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    this.studentService = new StudentService(studentMapper, repository);
  }

  @Test
  void when_create_student_then_ok() {
    var request = StudentServiceTestFixture.createStudentRequest();
    var entity = StudentServiceTestFixture.createStudentEntity(request);
    var domain = studentMapper.studentEntityToStudent(entity);
    when(repository.findByStudentId(any())).thenReturn(Optional.empty());
    when(repository.save(any())).thenReturn(entity);
    var result = studentService.createStudent(request);
    StepVerifier.create(result)
            .expectNext(domain)
            .verifyComplete();
  }

  @Test
  void when_create_existing_student_then_error() {
    var request = StudentServiceTestFixture.createStudentRequest();
    var entity = StudentServiceTestFixture.createStudentEntity();
    when(repository.findByStudentId(any())).thenReturn(Optional.of(entity));
    var result = studentService.createStudent(request);
    StepVerifier.create(result)
            .expectError(DuplicatedEntryException.class);

  }

  @Test
  void when_update_existing_course_then_ok() {
    var request = StudentServiceTestFixture.createUpdateStudentRequest();
    var entity = StudentServiceTestFixture.createStudentEntity(request);
    var updatedDomain = studentMapper.studentEntityToStudent(entity);
    var currentEntity = StudentServiceTestFixture.createStudentEntity();
    when(repository.findById(any())).thenReturn(Optional.of(currentEntity));
    when(repository.save(any())).thenReturn(entity);
    var result = studentService.updateStudent(request, UUID.randomUUID());
    StepVerifier.create(result)
            .expectNext(updatedDomain)
            .verifyComplete();
  }

  @Test
  void when_update_no_existing_course_then_error() {
    var request = StudentServiceTestFixture.createUpdateStudentRequest();
    when(repository.findById(any())).thenReturn(Optional.empty());
    var result = studentService.updateStudent(request, UUID.randomUUID());
    StepVerifier.create(result)
            .expectError(ObjectNotFoundException.class)
            .verify();
  }

  @Test
  void when_delete_existing_student_then_ok() {
    var entity = StudentServiceTestFixture.createStudentEntity();
    when(repository.findById(any())).thenReturn(Optional.of(entity));
    doNothing().when(repository).delete(entity);
    var result = studentService.deleteStudent(UUID.randomUUID());
    StepVerifier.create(result)
            .expectComplete();
  }

  @Test
  void when_delete_no_existing_student_then_error() {
    when(repository.findById(any())).thenReturn(Optional.empty());
    var result = studentService.deleteStudent(UUID.randomUUID());
    StepVerifier.create(result)
            .expectError(ObjectNotFoundException.class)
            .verify();
  }

  @Test
  void when_search_existing_student_by_student_id_then_ok() {
    var entity = StudentServiceTestFixture.createStudentEntity();
    var domain = studentMapper.studentEntityToStudent(entity);
    when(repository.findByStudentId(any())).thenReturn(Optional.of(entity));
    var result = studentService.getStudentByStudentId("xXXa");
    StepVerifier.create(result)
            .expectNext(domain)
            .verifyComplete();
  }

  @Test
  void when_search_no_existing_student_by_student_id_then_error() {
    when(repository.findByStudentId(any())).thenReturn(Optional.empty());
    var result = studentService.getStudentByStudentId("xXXa");
    StepVerifier.create(result)
            .expectError(ObjectNotFoundException.class)
            .verify();
  }

  @Test
  void when_search_students_whitouth_courses_then_ok() {
    var entities = StudentServiceTestFixture.getStudyEntityList();
    when(repository.findStudentsWithoutCourse()).thenReturn(entities);
    var result = studentService.studentsWithoutCourse();
    StepVerifier.create(result)
            .expectNextCount(entities.size())
            .verifyComplete();
  }
}
