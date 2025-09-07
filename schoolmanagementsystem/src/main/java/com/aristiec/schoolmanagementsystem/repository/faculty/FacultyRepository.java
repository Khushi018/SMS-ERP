package com.aristiec.schoolmanagementsystem.repository.faculty;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.constant.enums.FacultyDesignation;
import com.aristiec.schoolmanagementsystem.modal.faculty.Faculty;

@Repository
public interface FacultyRepository  extends JpaRepository<Faculty,Long>{
    List<Faculty> findByDepartmentId(Long departmentId);
 List<Faculty> findByPosition(FacultyDesignation position);
 long countByDepartmentId(Long departmentId);
long countByPosition(FacultyDesignation position);
 Optional<Faculty> findByEmail(String email);

  @Query("SELECT f FROM Faculty f JOIN f.courses c WHERE c.id = :courseId")
    List<Faculty> findByCourseId(@Param("courseId") Long courseId);


}
