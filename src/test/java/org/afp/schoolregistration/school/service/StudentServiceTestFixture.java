package org.afp.schoolregistration.school.service;

import com.github.javafaker.Faker;
import org.afp.schoolregistration.course.model.request.UpdateCourseRequest;
import org.afp.schoolregistration.student.entity.StudentEntity;
import org.afp.schoolregistration.student.model.request.CreateStudentRequest;
import org.afp.schoolregistration.student.model.request.UpdateStudentRequest;

import java.util.List;
import java.util.UUID;

public class StudentServiceTestFixture {

  private static final Faker faker = new Faker();

  public static CreateStudentRequest createStudentRequest() {
    var request = new CreateStudentRequest();
    request.setStudentId(faker.idNumber().ssnValid());
    request.setEmailAddress(faker.internet().emailAddress());
    request.setFirstName(faker.name().firstName());
    request.setLastName(faker.name().lastName());
    return request;
  }

  public static UpdateStudentRequest createUpdateStudentRequest() {
    var request = new UpdateStudentRequest();
    request.setStudentId(faker.idNumber().ssnValid());
    request.setEmailAddress(faker.internet().emailAddress());
    request.setFirstName(faker.name().firstName());
    request.setLastName(faker.name().lastName());
    return request;
  }

  public static StudentEntity createStudentEntity(CreateStudentRequest request) {
    var entity = new StudentEntity();
    entity.setStudentId(request.getStudentId());
    entity.setEmailAddress(request.getEmailAddress());
    entity.setFirstName(request.getFirstName());
    entity.setLastName(request.getLastName());
    entity.setId(UUID.randomUUID());
    return entity;
  }

  public static StudentEntity createStudentEntity(UpdateStudentRequest request) {
    var entity = new StudentEntity();
    entity.setStudentId(request.getStudentId());
    entity.setEmailAddress(request.getEmailAddress());
    entity.setFirstName(request.getFirstName());
    entity.setLastName(request.getLastName());
    entity.setId(UUID.randomUUID());
    return entity;
  }

  public static StudentEntity createStudentEntity() {
    var entity = new StudentEntity();
    entity.setStudentId(faker.idNumber().ssnValid());
    entity.setEmailAddress(faker.internet().emailAddress());
    entity.setFirstName(faker.name().firstName());
    entity.setLastName(faker.name().lastName());
    entity.setId(UUID.randomUUID());
    return entity;
  }

  public static List<StudentEntity> getStudyEntityList() {
    return List.of(createStudentEntity(), createStudentEntity(), createStudentEntity());
  }
}
