package org.afp.schoolregistration.course.service;

import org.afp.schoolregistration.course.domain.Course;
import reactor.core.publisher.Flux;

public interface EmptyCourseUseCase {

  Flux<Course> getCoursesWithoutStudents();
}
