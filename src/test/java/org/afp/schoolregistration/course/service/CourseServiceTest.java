package org.afp.schoolregistration.course.service;


import org.afp.schoolregistration.common.exceptions.DuplicatedEntryException;
import org.afp.schoolregistration.common.exceptions.ObjectNotFoundException;
import org.afp.schoolregistration.course.domain.CourseMapper;
import org.afp.schoolregistration.course.repository.CourseMySQLJPARepository;
import org.afp.schoolregistration.course.service.impl.CourseService;
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

public class CourseServiceTest {

  private CourseService courseService;

  @Mock
  private CourseMySQLJPARepository courseMySQLJPARepository;

  private final CourseMapper courseMapper = Mappers.getMapper(CourseMapper.class);


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    this.courseService = new CourseService(courseMySQLJPARepository, courseMapper);
  }

  @Test
  void when_create_course_then_ok() {
    var request = CourseServiceTestFixture.createCourseRequest();
    var entity = CourseServiceTestFixture.createCourseEntity(request);
    var domain = courseMapper.courseEntityToCourse(entity);
    when(courseMySQLJPARepository.findByCourseCode(any())).thenReturn(Optional.empty());
    when(courseMySQLJPARepository.save(any())).thenReturn(entity);
    var result = courseService.createCourse(request);
    StepVerifier.create(result)
            .expectNext(domain)
            .verifyComplete();
  }

  @Test
  void when_create_course_and_already_exists_then_error() {
    var request = CourseServiceTestFixture.createCourseRequest();
    var entity = CourseServiceTestFixture.createCourseEntity();
    when(courseMySQLJPARepository.findByCourseCode(any())).thenReturn(Optional.of(entity));
    var result = courseService.createCourse(request);
    StepVerifier.create(result)
            .expectError(DuplicatedEntryException.class)
            .verify();
  }

  @Test
  void when_update_existing_course_then_ok() {
    var request = CourseServiceTestFixture.createUpdateRequest();
    var existingEntity = CourseServiceTestFixture.createCourseEntity();
    var entity = CourseServiceTestFixture.createCourseEntity(request);
    var domain = courseMapper.courseEntityToCourse(entity);
    when(courseMySQLJPARepository.findById(any())).thenReturn(Optional.of(existingEntity));
    when(courseMySQLJPARepository.save(any())).thenReturn(entity);
    var result = courseService.updateCourse(request, UUID.randomUUID());
    StepVerifier.create(result)
            .expectNext(domain)
            .verifyComplete();
  }

  @Test
  void when_update_non_existing_course_then_error() {
    var request = CourseServiceTestFixture.createUpdateRequest();
    when(courseMySQLJPARepository.findById(any())).thenReturn(Optional.empty());
    var result = courseService.updateCourse(request, UUID.randomUUID());
    StepVerifier.create(result)
            .expectError(ObjectNotFoundException.class)
            .verify();
  }

  @Test
  void when_delete_existing_course_then_ok() {
    var entity = CourseServiceTestFixture.createCourseEntity();
    when(courseMySQLJPARepository.findById(entity.getId())).thenReturn(Optional.of(entity));
    doNothing().when(courseMySQLJPARepository).delete(entity);
    var result = courseService.deleteCourse(entity.getId());
    StepVerifier.create(result)
            .expectComplete();
  }

  @Test
  void when_delete_no_existing_course_then_error() {
    when(courseMySQLJPARepository.findById(any())).thenReturn(Optional.empty());
    var result = courseService.deleteCourse(UUID.randomUUID());
    StepVerifier.create(result)
            .expectError(ObjectNotFoundException.class)
            .verify();
  }

  @Test
  void when_get_existing_course_by_code_then_ok() {
    var entity = CourseServiceTestFixture.createCourseEntity();
    var domain = courseMapper.courseEntityToCourse(entity);
    when(courseMySQLJPARepository.findByCourseCode(any())).thenReturn(Optional.of(entity));
    var result = courseService.getCourseByCode("AB");
    StepVerifier.create(result)
            .expectNext(domain)
            .verifyComplete();
  }

  @Test
  void when_get_no_existing_course_by_code_then_error() {
    when(courseMySQLJPARepository.findByCourseCode(any())).thenReturn(Optional.empty());
    var result = courseService.getCourseByCode("AB");
    StepVerifier.create(result)
            .expectError(ObjectNotFoundException.class)
            .verify();
  }

  @Test
  void when_get_all_courses_then_ok() {
    var entityList = CourseServiceTestFixture.getCourseEntityList();
    when(courseMySQLJPARepository.findAll()).thenReturn(entityList);
    var result = courseService.getAllCourses();
    StepVerifier.create(result)
            .expectNextCount(entityList.size())
            .verifyComplete();
  }

  @Test
  void when_get_courses_without_students_then_ok() {
    var entityList = CourseServiceTestFixture.getCourseEntityList();
    when(courseMySQLJPARepository.findCoursesWithoutStudents()).thenReturn(entityList);
    var result = courseService.getCoursesWithoutStudents();
    StepVerifier.create(result)
            .expectNextCount(entityList.size())
            .verifyComplete();
  }
}
