package com.aristiec.schoolmanagementsystem.repository.attendance;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.constant.enums.AttendanceStatus;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.attendance.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    List<Attendance> findByStudentDetailsId(Long studentId);
    List<Attendance> findByStudentDetails(StudentDetails student);

 boolean existsByStudentDetailsIdAndTimetableIdAndDate(Long studentId, Long timetableId, LocalDate date);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.studentDetails.id = :studentId")
    long countTotalByStudentId(Long studentId);

       @Query("SELECT COUNT(a) FROM Attendance a WHERE a.studentDetails.id = :studentId AND a.attendanceStatus = 'PRESENT'")
    long countPresentByStudentId(Long studentId);

       @Query("SELECT COUNT(a) FROM Attendance a WHERE a.studentDetails.course.id = :courseId")
    long countTotalByCourseId(Long courseId); 

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.studentDetails.course.id = :courseId AND a.attendanceStatus = 'PRESENT'")
    long countPresentByCourseId(Long courseId);
   
     
     @Query("SELECT COUNT(a) FROM Attendance a WHERE a.studentDetails.id = :studentId AND a.attendanceStatus = :status")
    int countByStudentIdAndStatus(Long studentId, AttendanceStatus status);


@Query("""
    SELECT COUNT(a)
    FROM Attendance a
    WHERE a.studentDetails.id = :studentId
    AND a.timetable.subject.semester = :semester
""")
Long countByStudentIdAndSemester(@Param("studentId") Long studentId, @Param("semester") Integer semester);


@Query("""
    SELECT COUNT(a)
    FROM Attendance a
    WHERE a.studentDetails.id = :studentId
    AND a.attendanceStatus = :status
    AND a.timetable.subject.semester = :semester
""")
Long countByStudentIdAndStatusAndSemester(
    @Param("studentId") Long studentId,
    @Param("status") AttendanceStatus status,
    @Param("semester") Integer semester
);




} 
