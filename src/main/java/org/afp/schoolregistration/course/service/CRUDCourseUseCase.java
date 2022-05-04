package org.afp.schoolregistration.course.service;

import org.afp.schoolregistration.course.domain.Course;
import org.afp.schoolregistration.course.model.request.CreateCourseRequest;
import org.afp.schoolregistration.course.model.request.UpdateCourseRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CRUDCourseUseCase {

  Mono<Course> createCourse(CreateCourseRequest createCourseRequest);
  Mono<Course> updateCourse(UpdateCourseRequest updateCourseRequest, UUID id);
  Mono<Void> deleteCourse(UUID id);
  Mono<Course> getCourseByCode(String code);
  Flux<Course> getAllCourses();

}
