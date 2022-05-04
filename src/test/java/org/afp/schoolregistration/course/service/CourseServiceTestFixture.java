package org.afp.schoolregistration.course.service;

import com.github.javafaker.Faker;
import org.afp.schoolregistration.course.entity.CourseEntity;
import org.afp.schoolregistration.course.model.request.CreateCourseRequest;
import org.afp.schoolregistration.course.model.request.UpdateCourseRequest;

import java.util.List;
import java.util.UUID;

public class CourseServiceTestFixture {

  private static final Faker faker = new Faker();

  public static CreateCourseRequest createCourseRequest() {
    var request = new CreateCourseRequest();
    request.setCourseCode(faker.random().hex());
    request.setCourseName(faker.educator().course());
    return request;
  }

  public static UpdateCourseRequest createUpdateRequest() {
    var request = new UpdateCourseRequest();
    request.setCourseCode(faker.random().hex());
    request.setCourseName(faker.educator().course());
    return request;
  }

  public static CourseEntity createCourseEntity(CreateCourseRequest createCourseRequest){
    var entity = new CourseEntity();
    entity.setCourseCode(createCourseRequest.getCourseCode());
    entity.setCourseName(createCourseRequest.getCourseName());
    entity.setId(UUID.randomUUID());
    return entity;
  }

  public static List<CourseEntity> getCourseEntityList() {
    return List.of(createCourseEntity(), createCourseEntity(), createCourseEntity());
  }

  public static CourseEntity createCourseEntity(UpdateCourseRequest update){
    var entity = new CourseEntity();
    entity.setCourseCode(update.getCourseCode());
    entity.setCourseName(update.getCourseName());
    entity.setId(UUID.randomUUID());
    return entity;
  }

  public static CourseEntity createCourseEntity() {
    var entity = new CourseEntity();
    entity.setCourseCode(faker.random().hex());
    entity.setCourseName(faker.educator().course());
    entity.setId(UUID.randomUUID());
    return entity;
  }

}
