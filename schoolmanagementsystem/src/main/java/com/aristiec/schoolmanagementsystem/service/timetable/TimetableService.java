package com.aristiec.schoolmanagementsystem.service.timetable;

import java.util.List;

import com.aristiec.schoolmanagementsystem.constant.enums.DayOfWeek;
import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;
import com.aristiec.schoolmanagementsystem.dto.timetable.TimeTableDTO;
import com.aristiec.schoolmanagementsystem.dto.timetable.TimetableRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.timetable.TimetableResponseDTO;
import com.aristiec.schoolmanagementsystem.modal.timetable.Timetable;

public interface TimetableService {
 TimetableResponseDTO saveTimetableList(TimetableRequestDTO requestDTO);
    TimetableResponseDTO getTimetableForStudent(Long studentId);
    List<TimeTableDTO> getAllTimetables();
   List<TimeTableDTO> getTimetableByDay(DayOfWeek day);
   List<TimeTableDTO> getTimetableByFacultyId(Long facultyId);
   List<TimeTableDTO> getTimetableByStudentIdAndDay(Long studentId, DayOfWeek day);
   List<TimeTableDTO> getTimetableByFacultyIdAndDay(Long facultyId, DayOfWeek day);
   List<TimeTableDTO> getTimetableBySubjectId(Long subjectId);
   void updateTimetableById(Long id, TimetableRequestDTO requestDTO);
   List<TimeTableDTO> getTimetableBySection(Long courseId, SectionEnum section);
   List<TimeTableDTO> getTimetableByCourseIdAndSemester(Long courseId, int semester);
   TimetableResponseDTO getTimetableByCourseSectionAndSemester(Long courseId, SectionEnum section, Integer semester);










}
