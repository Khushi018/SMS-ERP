package com.aristiec.schoolmanagementsystem.repository.details;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.modal.admission.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseCode(String code);

    Optional<Course> findById(Long id);

    @Query("SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId")
    Course findCourseByStudentId(@Param("studentId") Long studentId);

}
