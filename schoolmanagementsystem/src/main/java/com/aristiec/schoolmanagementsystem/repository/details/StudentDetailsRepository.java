package com.aristiec.schoolmanagementsystem.repository.details;

import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;
import com.aristiec.schoolmanagementsystem.dto.adminReport.EnrollmentTrendDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.StudentCountByCourseDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.Address;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.admission.ParentDetails;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;

import jakarta.transaction.Transactional;

@Repository
public interface StudentDetailsRepository
        extends JpaRepository<StudentDetails, Long>, JpaSpecificationExecutor<StudentDetails> {

    @Query("SELECT s.parentDetails FROM StudentDetails s WHERE s.id = :studentId")
    ParentDetails findParentDetailsByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT s.course FROM StudentDetails s WHERE s.id = :studentId")
    Course findCourseByStudentId(@Param("studentId") Long studentId);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE student_details AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

    @Query("SELECT s.address FROM StudentDetails s WHERE s.id = :studentId")
    List<Address> findAddressesByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT s FROM StudentDetails s WHERE s.id = :studentId")
    StudentDetails getStudentDetails(@Param("studentId") Long studentId);

    Optional<StudentDetails> findByStudentCode(String studentCode);

    Optional<StudentDetails> findByEmail(String studentCode);

    long countBySectionAndCourseAndYear(SectionEnum section, Course course, String year);

    long countByCourseAndYear(Course course, String year);

    List<StudentDetails> findByYear(String year);

    List<StudentDetails> findByCourse(Course course);

    List<StudentDetails> findByCourseAndYear(Course course, String year);

    List<StudentDetails> findByCourseAndYearAndSection(Course course, String year, SectionEnum section);

    List<StudentDetails> findByCourseOrYearOrSection(Course course, String year, SectionEnum section);

    @Query("SELECT new com.aristiec.schoolmanagementsystem.dto.adminReport.StudentCountByCourseDTO(s.course.courseName, COUNT(s)) "
            +
            "FROM StudentDetails s GROUP BY s.course.courseName")
    List<StudentCountByCourseDTO> countStudentsByCourse();

    @Query("SELECT CAST(s.gender AS string), COUNT(s) FROM StudentDetails s GROUP BY s.gender")
    List<Object[]> countByGender();

    @Query(value = """
                SELECT
                    CASE
                        WHEN TIMESTAMPDIFF(YEAR, s.date_of_birth, CURDATE()) BETWEEN 10 AND 14 THEN '10-14'
                        WHEN TIMESTAMPDIFF(YEAR, s.date_of_birth, CURDATE()) BETWEEN 15 AND 19 THEN '15-19'
                        WHEN TIMESTAMPDIFF(YEAR, s.date_of_birth, CURDATE()) BETWEEN 20 AND 24 THEN '20-24'
                        ELSE '25+'
                    END AS ageGroup,
                    COUNT(*) AS count
                FROM student_details s
                GROUP BY ageGroup
            """, nativeQuery = true)
    List<Object[]> countByAgeGroupNative();

    @Query("SELECT new com.aristiec.schoolmanagementsystem.dto.adminReport.EnrollmentTrendDTO(YEAR(s.createdAt), COUNT(s)) FROM StudentDetails s GROUP BY YEAR(s.createdAt) ORDER BY YEAR(s.createdAt)")
    List<EnrollmentTrendDTO> getEnrollmentTrends();

    List<StudentDetails> findByCourseId(Long courseId);

    @Query("SELECT SUM(c.totalFees) FROM StudentDetails s JOIN s.course c")
    Double getTotalCourseFeeByAllStudents();

}
