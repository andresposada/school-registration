package org.afp.schoolregistration.registration.repository;

import org.afp.schoolregistration.registration.entity.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegistrationMySQLJPARepository extends JpaRepository<RegistrationEntity, UUID> {

  List<RegistrationEntity> findByCourse_id(UUID studentId);

  List<RegistrationEntity> findByCourse_courseCode(String courseCode);

  List<RegistrationEntity> findByStudent_studentId(String studentId);

}
