package com.aristiec.schoolmanagementsystem.repository.timetable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.constant.enums.DayOfWeek;
import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;
import com.aristiec.schoolmanagementsystem.modal.faculty.Faculty;
import com.aristiec.schoolmanagementsystem.modal.timetable.Timetable;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable,Long>  {
        List<Timetable> findByCourseId(Long courseId);
        List<Timetable> findByDay(DayOfWeek day);
        List<Timetable> findByFaculty_Id(Long facultyId);
        List<Timetable> findByCourseIdAndDay(Long courseId, DayOfWeek day);
        List<Timetable> findByFacultyIdAndDay(Long facultyId, DayOfWeek day);
        List<Timetable> findBySubject_Id(Long subjectId);
        List<Timetable> findByCourseIdAndSection(Long courseId, SectionEnum section);
         List<Timetable> findByCourseIdAndSectionAndDay(Long courseId, SectionEnum section, DayOfWeek day);

@Query("SELECT t.faculty, t.section FROM Timetable t WHERE t.subject.id = :subjectId AND t.section = :section")
List<Object[]> findFacultyAndSectionBySubjectIdAndSection(@Param("subjectId") Long subjectId, @Param("section") SectionEnum section);


@Query("SELECT DISTINCT t.faculty FROM Timetable t WHERE t.subject.id = :subjectId")
List<Faculty> findFacultiesBySubjectId(@Param("subjectId") Long subjectId);


@Query("SELECT t FROM Timetable t WHERE t.course.id = :courseId AND t.subject.semester = :semester")
List<Timetable> findByCourseIdAndSubjectSemester(@Param("courseId") Long courseId, @Param("semester") int semester);


@Query("SELECT DISTINCT t.subject, t.section, t.course.courseName FROM Timetable t WHERE t.faculty.id = :facultyId")
List<Object[]> findDistinctSubjectSectionCourseByFacultyId(@Param("facultyId") Long facultyId);


List<Timetable> findByCourseIdAndSectionAndSemester(Long courseId, SectionEnum section, Integer semester);



}
