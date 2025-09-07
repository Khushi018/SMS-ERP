package com.aristiec.schoolmanagementsystem.repository.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamStatusEnum;
import com.aristiec.schoolmanagementsystem.modal.exam.Exam;
import com.aristiec.schoolmanagementsystem.modal.exam.ExamAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExamAttendanceRepository extends JpaRepository<ExamAttendance, Long> {
    @Query("SELECT ea FROM ExamAttendance ea WHERE ea.student.id = :studentId AND ea.exam.id = :examId")
    Optional<ExamAttendance> findByStudentIdAndExamId(Long studentId, Long examId);

    @Query("SELECT ea FROM ExamAttendance ea WHERE ea.exam.id = :examId")
    Optional<List<ExamAttendance>> findByExamId(Long examId);

    @Query("SELECT ea FROM ExamAttendance ea WHERE ea.student.id = :studentId")
    Optional<List<ExamAttendance>> findByStudentId(Long studentId);

    @Query("SELECT ea FROM ExamAttendance ea WHERE ea.student.id = :studentId AND ea.exam.examSchedule.sem = :sem AND ea.exam.status = :status")
    List<ExamAttendance> getStudentStatus(Long studentId, int sem, ExamStatusEnum status);

    @Query("""
            SELECT e FROM ExamAttendance ea
            JOIN ea.exam e
            JOIN e.examSchedule es
            WHERE ea.student.id = :studentId
            AND ea.status = 'ABSENT'
            AND es.sem = :sem
            """)
    List<Exam> findExamsByStudentIdAndStatusAbsentAndSemester(@Param("studentId") long studentId, @Param("sem") int sem);
}
