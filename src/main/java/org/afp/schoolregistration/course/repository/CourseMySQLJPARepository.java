package org.afp.schoolregistration.course.repository;

import org.afp.schoolregistration.course.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseMySQLJPARepository extends JpaRepository<CourseEntity, UUID> {

  Optional<CourseEntity> findByCourseCode(String courseCode);

  @Query("select c from CourseEntity c LEFT JOIN RegistrationEntity r on c.id = r.course.id where r.id is null")
  List<CourseEntity> findCoursesWithoutStudents();
}
