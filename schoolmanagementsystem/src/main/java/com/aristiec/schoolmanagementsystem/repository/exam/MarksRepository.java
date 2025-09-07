package com.aristiec.schoolmanagementsystem.repository.exam;

import java.util.List;

import com.aristiec.schoolmanagementsystem.constant.enums.GradeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.SubjectWiseComparisonDTO;
import com.aristiec.schoolmanagementsystem.modal.exam.Marks;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Long> {

    @Query("SELECT m FROM Marks m WHERE m.exam.examId = :examId")
    List<Marks> findAllMarksByExamId(@Param("examId") long examId);

    @Query("SELECT m FROM Marks m WHERE m.student.id = :studentId AND m.exam.examId = :examId")
    Marks findMarksByStudentAndExam(@Param("studentId") Long studentId, @Param("examId") long examId);

    @Query("SELECT m FROM Marks m WHERE m.student.id = :studentId")
    List<Marks> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT m.student.id FROM Marks m WHERE m.grade = :grade")
    List<Long> findStudentWithGrade(GradeEnum grade);

    @Query("SELECT m FROM Marks m WHERE m.student.id = :studentId AND m.exam.examSchedule.examScheduleId = :schId")
    List<Marks> findStudentMarksWithSchId(Long studentId, long schId);

    
      @Query("SELECT m FROM Marks m " +
       "JOIN FETCH m.exam e " +
       "JOIN FETCH e.subject s " +
       "WHERE m.student.id = :studentId")
List<Marks> findDetailedMarksByStudentId(@Param("studentId") Long studentId);




@Query("""
    SELECT m
    FROM Marks m
    WHERE m.student.id = :studentId
    AND m.exam.subject.semester = :semester
""")
List<Marks> findByStudentIdAndSemester(Long studentId, Integer semester);

    @Query("""
        SELECT m
        FROM Marks m
        JOIN m.exam e
        JOIN e.subject s
        JOIN e.examSchedule sch
        WHERE m.student.id = :studentId
        AND sch.sem = :semester
        AND e.examSchedule.examType = :examType
    """)
    List<Marks> findStudentResults(
            @Param("studentId") Long studentId,
            @Param("semester") Integer semester,
            @Param("examType") ExamTypeEnum examType
    );

}
