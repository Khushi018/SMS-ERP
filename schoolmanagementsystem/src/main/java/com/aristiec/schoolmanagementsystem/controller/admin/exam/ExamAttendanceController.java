package com.aristiec.schoolmanagementsystem.controller.admin.exam;

import com.aristiec.schoolmanagementsystem.dto.exam.ExamAttendanceCreateDto;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamAttendanceReadDto;
import com.aristiec.schoolmanagementsystem.service.exam.ExamAttendanceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exam-attendance")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Exam Attendance Management", description = "APIs for managing exam attendance in the school management system")
public class ExamAttendanceController {

    private final ExamAttendanceService examAttendanceService;



    @PostMapping
    public ResponseEntity<ExamAttendanceReadDto> createExamAttendance(
            @RequestBody @Valid ExamAttendanceCreateDto createDto) {
        ExamAttendanceReadDto createdDto = examAttendanceService.createExamAttendance(createDto);
        return ResponseEntity.ok(createdDto);
    }

    // Get attendance by studentId and examId
    @GetMapping("/{studentId}/{examId}")
    public ResponseEntity<ExamAttendanceReadDto> getExamAttendance(
            @PathVariable Long studentId, @PathVariable Long examId) {
        ExamAttendanceReadDto readDto = examAttendanceService.getExamAttendance(studentId, examId);
        return ResponseEntity.ok(readDto);
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<ExamAttendanceReadDto>> getExamAttendanceByExam(@PathVariable Long examId) {
        List<ExamAttendanceReadDto> readDto = examAttendanceService.getExamAttendanceByExam( examId);
        return ResponseEntity.ok(readDto);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ExamAttendanceReadDto>> getExamAttendance(@PathVariable Long studentId) {
        List<ExamAttendanceReadDto> readDto = examAttendanceService.getExamAttendanceByStudent(studentId);
        return ResponseEntity.ok(readDto);
    }


    @GetMapping
    public ResponseEntity<List<ExamAttendanceReadDto>> getAllExamAttendances() {
        List<ExamAttendanceReadDto> readDtos = examAttendanceService.getAllExamAttendances();
        return ResponseEntity.ok(readDtos);
    }

    @DeleteMapping("/{studentId}/{examId}")
    public ResponseEntity<Void> deleteExamAttendance(
            @PathVariable Long studentId, @PathVariable Long examId) {
        examAttendanceService.deleteExamAttendance(studentId, examId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/student-status/{studentId}/{sem}")
    public ResponseEntity<List<ExamAttendanceReadDto>> getStudentStatus(
            @PathVariable Long studentId, @PathVariable int sem
    ) {
        List<ExamAttendanceReadDto> readDtos = examAttendanceService.getStudentStatus(studentId,sem);
        return ResponseEntity.ok(readDtos);
    }
}
