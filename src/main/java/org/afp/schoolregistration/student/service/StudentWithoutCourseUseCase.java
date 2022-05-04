package org.afp.schoolregistration.student.service;

import org.afp.schoolregistration.student.domain.Student;
import reactor.core.publisher.Flux;

public interface StudentWithoutCourseUseCase {

  Flux<Student> studentsWithoutCourse();
}
