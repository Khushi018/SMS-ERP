package com.aristiec.schoolmanagementsystem.controller.timetable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.constant.enums.DayOfWeek;
import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;
import com.aristiec.schoolmanagementsystem.dto.timetable.TimeTableDTO;
import com.aristiec.schoolmanagementsystem.dto.timetable.TimetableRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.timetable.TimetableResponseDTO;
import com.aristiec.schoolmanagementsystem.service.timetable.TimetableService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/timetable")
@SecurityRequirement(name = "bearerAuth") 
@Tag(name = "Timetable Management", description = "Manage timetables for students, faculty, and courses")
public class TimetableController {
     @Autowired
    private TimetableService timetableService;

    @PostMapping
public ResponseEntity<TimetableResponseDTO> saveTimetable(@RequestBody TimetableRequestDTO requestDTO) {
    TimetableResponseDTO response = timetableService.saveTimetableList(requestDTO);
    return ResponseEntity.ok(response);
}


     @GetMapping("/student/{studentId}")
    public ResponseEntity<TimetableResponseDTO> getTimetable(@PathVariable Long studentId) {
        return ResponseEntity.ok(timetableService.getTimetableForStudent(studentId));
    }

    @GetMapping
public ResponseEntity<List<TimeTableDTO>> getAllTimetables() {
    return ResponseEntity.ok(timetableService.getAllTimetables());
}

@GetMapping("/by-day")
public ResponseEntity<List<TimeTableDTO>> getTimetableByDay(@RequestParam DayOfWeek day) {
    return ResponseEntity.ok(timetableService.getTimetableByDay(day));
}

@GetMapping("/by-faculty/{facultyId}")
public ResponseEntity<List<TimeTableDTO>> getTimetableByFaculty(@PathVariable Long facultyId) {
    return ResponseEntity.ok(timetableService.getTimetableByFacultyId(facultyId));
}


@GetMapping("/student/{studentId}/by-day")
public ResponseEntity<List<TimeTableDTO>> getTimetableByStudentIdAndDay(
        @PathVariable Long studentId,
        @RequestParam DayOfWeek day) {
    return ResponseEntity.ok(timetableService.getTimetableByStudentIdAndDay(studentId, day));
}

@GetMapping("/faculty/{facultyId}/by-day")
public ResponseEntity<List<TimeTableDTO>> getTimetableByFacultyIdAndDay(
        @PathVariable Long facultyId,
        @RequestParam DayOfWeek day) {
    return ResponseEntity.ok(timetableService.getTimetableByFacultyIdAndDay(facultyId, day));
}

@GetMapping("/by-subject/{subjectId}")
public ResponseEntity<List<TimeTableDTO>> getTimetableBySubjectId(@PathVariable Long subjectId) {
    return ResponseEntity.ok(timetableService.getTimetableBySubjectId(subjectId));
}


@PutMapping("update/{id}")
public ResponseEntity<String> updateTimetable(@PathVariable Long id, @RequestBody TimetableRequestDTO requestDTO) {
    timetableService.updateTimetableById(id, requestDTO);
    return ResponseEntity.ok("Timetable updated successfully.");
}


@GetMapping("/course/{courseId}/section/{section}")
public ResponseEntity<List<TimeTableDTO>> getTimetableByCourseAndSection(
        @PathVariable Long courseId,
        @PathVariable SectionEnum section) {
    List<TimeTableDTO> response = timetableService.getTimetableBySection(courseId, section);
    return ResponseEntity.ok(response);
}


@GetMapping("/by-course-and-semester")
public ResponseEntity<List<TimeTableDTO>> getByCourseIdAndSemester(
        @RequestParam Long courseId,
        @RequestParam int semester) {
    return ResponseEntity.ok(timetableService.getTimetableByCourseIdAndSemester(courseId, semester));
}


@GetMapping("/by-course-section-semester")
public ResponseEntity<TimetableResponseDTO> getTimetableByCourseSectionAndSemester(
        @RequestParam Long courseId,
        @RequestParam SectionEnum section,
        @RequestParam Integer semester) {
    return ResponseEntity.ok(timetableService.getTimetableByCourseSectionAndSemester(courseId, section, semester));
}







     
}
