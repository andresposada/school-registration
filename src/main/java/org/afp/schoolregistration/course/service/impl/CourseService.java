package org.afp.schoolregistration.course.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.afp.schoolregistration.common.exceptions.DuplicatedEntryException;
import org.afp.schoolregistration.common.exceptions.ObjectNotFoundException;
import org.afp.schoolregistration.course.domain.Course;
import org.afp.schoolregistration.course.domain.CourseMapper;
import org.afp.schoolregistration.course.model.request.CreateCourseRequest;
import org.afp.schoolregistration.course.model.request.UpdateCourseRequest;
import org.afp.schoolregistration.course.repository.CourseMySQLJPARepository;
import org.afp.schoolregistration.course.service.CRUDCourseUseCase;
import org.afp.schoolregistration.course.service.EmptyCourseUseCase;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CourseService implements CRUDCourseUseCase, EmptyCourseUseCase {

  final CourseMySQLJPARepository courseMySQLJPARepository;
  final CourseMapper courseMapper;

  @Override
  public Mono<Course> createCourse(CreateCourseRequest createCourseRequest) {
    return Mono.just( createCourseRequest)
            .flatMap(request -> Mono.just(courseMySQLJPARepository.findByCourseCode(request.getCourseCode())))
            .flatMap(optEntity -> {
              if (optEntity.isPresent()) {
                return Mono.error(new DuplicatedEntryException(format("Course with code {0} already exists", createCourseRequest.getCourseCode())));
              }
              var domain = courseMapper.createCourseRequestToCourse(createCourseRequest);
              return Mono.just(courseMapper.courseToCourseEntity(domain));

            })
            .flatMap(courseEntity -> Mono.just(courseMySQLJPARepository.save(courseEntity)))
            .map(courseMapper::courseEntityToCourse);
  }

  @Override
  public Mono<Course> updateCourse(UpdateCourseRequest updateCourseRequest, UUID id) {
    return Mono.just(id)
            .flatMap(courseId -> Mono.just(courseMySQLJPARepository.findById(courseId)))
            .flatMap(optEntity -> optEntity.map(entity -> {
              courseMapper.updateCourseEntity(entity, courseMapper.updateCourseRequestToCourse(updateCourseRequest));
              return Mono.just(entity);
            }).orElseThrow(() -> new ObjectNotFoundException(format("Course with id {0} was not found", id))))
            .flatMap(entity -> Mono.just(courseMySQLJPARepository.save(entity)))
            .map(courseMapper::courseEntityToCourse);
  }

  @Override
  public Mono<Void> deleteCourse(UUID id) {
    return Mono.just(id)
            .flatMap(courseId -> Mono.just(courseMySQLJPARepository.findById(courseId)))
            .flatMap(optEntity -> optEntity.map(courseEntity -> {
              courseMySQLJPARepository.delete(courseEntity);
              return Mono.empty();
            }).orElse(Mono.error(new ObjectNotFoundException(format("Course with id {0} was not found", id)))))
            .then();
  }

  @Override
  public Mono<Course> getCourseByCode(String code) {
    return Mono.just(code)
            .flatMap(courseCode -> Mono.just(courseMySQLJPARepository.findByCourseCode(courseCode)))
            .flatMap(optEntity -> optEntity.map(entity -> Mono.just(courseMapper.courseEntityToCourse(entity)))
                    .orElseThrow(() -> new ObjectNotFoundException(format("Course with code {0} was not found", code))))
            ;
  }

  @Override
  public Flux<Course> getAllCourses() {
    return Flux.fromIterable(courseMySQLJPARepository.findAll()).map(courseMapper::courseEntityToCourse);
  }

  @Override
  public Flux<Course> getCoursesWithoutStudents() {
    return Flux.fromIterable(courseMySQLJPARepository.findCoursesWithoutStudents()
            .stream()
            .map(courseMapper::courseEntityToCourse)
            .collect(Collectors.toList()));
  }
}
