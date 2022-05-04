package org.afp.schoolregistration.registration.service;

import org.afp.schoolregistration.registration.domain.Registration;
import org.afp.schoolregistration.registration.model.request.CreateRegistrationRequest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface RegistrationUseCase {

  Mono<Registration> addRegistration(CreateRegistrationRequest createRegistrationRequest);

  Map<String, List<Registration>> getStudentsByCourse(String courseCode);

  Map<String, List<Registration>> getCourseByStudent(String studentId);

}
