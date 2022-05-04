package org.afp.schoolregistration.course.domain;

import org.afp.schoolregistration.course.entity.CourseEntity;
import org.afp.schoolregistration.course.model.request.CreateCourseRequest;
import org.afp.schoolregistration.course.model.request.UpdateCourseRequest;
import org.afp.schoolregistration.course.model.response.CourseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CourseMapper {

  Course createCourseRequestToCourse(CreateCourseRequest createCourseRequest);

  Course updateCourseRequestToCourse(UpdateCourseRequest updateCourseRequest);

  @Mapping(target = "students", ignore = true)
  Course courseEntityToCourse(CourseEntity courseEntity);

  CourseEntity courseToCourseEntity(Course course);

  CourseResponse courseToCourseResponse(Course course);

  void updateCourseEntity(@MappingTarget CourseEntity courseEntity, Course course);

}
