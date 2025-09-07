package com.aristiec.schoolmanagementsystem.repository.exam;

import java.util.List;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.modal.exam.Exam;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    @Query("SELECT e FROM Exam e WHERE e.examSchedule.examScheduleId = :id")
    List<Exam> findByExamSchedule(Long id);

    @Query("SELECT e FROM Exam e WHERE e.examSchedule.course.Id = :courseId")
    List<Exam> findExamsByCourseId(Long courseId);

    @Query("SELECT e FROM Exam e WHERE e.examSchedule.examType = :examType")
    List<Exam> findByExamType(ExamTypeEnum examType);

    @Query("SELECT e FROM Exam e WHERE e.examSchedule.examType = :examType AND e.examSchedule.course.id = :courseId")
    List<Exam> findByExamTypeAndCourseId(@Param("examType") ExamTypeEnum examType, @Param("courseId") Long courseId);

    List<Exam> findByStatus(ExamStatusEnum examStatus);

    @Query("SELECT e FROM Exam e WHERE e.examSchedule.examScheduleId = :scheduleId AND e.examSchedule.examType = :examType")
    List<Exam> findByScheduleIdAndExamType(@Param("scheduleId") Long scheduleId, @Param("examType") ExamTypeEnum examType);
}
