package org.afp.schoolregistration.student.repository;

import org.afp.schoolregistration.student.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentMySQLJPARepository extends JpaRepository<StudentEntity, UUID> {

  Optional<StudentEntity> findByStudentId(String studentId);

  @Query("select s from StudentEntity s LEFT JOIN RegistrationEntity r on s.id = r.student.id where r.id is null")
  List<StudentEntity> findStudentsWithoutCourse();
}
