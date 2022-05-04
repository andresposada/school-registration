package org.afp.schoolregistration.student.domain;

import org.afp.schoolregistration.registration.domain.Registration;
import org.afp.schoolregistration.registration.entity.RegistrationEntity;
import org.afp.schoolregistration.student.entity.StudentEntity;
import org.afp.schoolregistration.student.model.request.CreateStudentRequest;
import org.afp.schoolregistration.student.model.request.UpdateStudentRequest;
import org.afp.schoolregistration.student.model.response.StudentFullDataResponse;
import org.afp.schoolregistration.student.model.response.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "courses", ignore = true)
  Student createStudentRequestToStudent(CreateStudentRequest createStudentRequest);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "courses", ignore = true)
  Student updateStudentRequestToStudent(UpdateStudentRequest updateStudentRequest);

  @Mapping(target = "courses", ignore = true)
  Student studentEntityToStudent(StudentEntity entity);

  StudentEntity studentToStudentEntity(Student student);

  StudentResponse studentToStudentResponse(Student student);

  @Mapping(target = "student", expression = "java(null)")
  Registration registrationEntityToRegistration(RegistrationEntity registrationEntity);

  StudentFullDataResponse studentToStudentFullDataResponse(Student student);

  void updateStudentEntity(@MappingTarget StudentEntity entity, Student student);

}
