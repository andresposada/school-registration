package org.afp.schoolregistration.registration.domain;

import org.afp.schoolregistration.course.domain.CourseMapper;
import org.afp.schoolregistration.registration.entity.RegistrationEntity;
import org.afp.schoolregistration.registration.model.response.RegistrationByCourseResponse;
import org.afp.schoolregistration.registration.model.response.RegistrationResponse;
import org.afp.schoolregistration.student.domain.StudentMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {StudentMapper.class, CourseMapper.class})
public interface RegistrationMapper {

  Registration registrationEntityToRegistration(RegistrationEntity registrationEntity);

  RegistrationEntity registrationToRegistrationEntity(Registration registration);

  @Mapping(source = "registration.student.studentId", target = "studentId")
  @Mapping(source = "registration.course.courseName", target = "courseName")
  @Mapping(source = "registration.id", target = "id")
  RegistrationResponse registrationToRegistrationResponse(Registration registration);

  @Mapping(target = "courseName", ignore = true)
  @Mapping(target = "students", ignore = true)
  RegistrationByCourseResponse registrationToRegistrationByCourseResponse(Registration registration);
}
