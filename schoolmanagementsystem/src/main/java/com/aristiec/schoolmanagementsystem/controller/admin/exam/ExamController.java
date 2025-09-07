package com.aristiec.schoolmanagementsystem.controller.admin.exam;

import java.util.ArrayList;
import java.util.List;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamStatusEnum;
import com.aristiec.schoolmanagementsystem.modal.assignment.Subject;
import com.aristiec.schoolmanagementsystem.repository.subject.SubjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamCreateDto;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamDTO;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamHistoryDTO;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamMapper;
import com.aristiec.schoolmanagementsystem.modal.exam.Exam;
import com.aristiec.schoolmanagementsystem.modal.exam.ExamSchedule;
import com.aristiec.schoolmanagementsystem.service.exam.ExamScheduleService;
import com.aristiec.schoolmanagementsystem.service.exam.ExamService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/exams")
@SecurityRequirement(name = "bearerAuth") 
@RequiredArgsConstructor
@Tag(name = "Exam Management", description = "APIs for managing exams in the school management system")
public class ExamController {

    private final ExamService examService;
    private final ExamScheduleService examScheduleService;
    private final SubjectRepository subjectRepository;
    private final ExamMapper examMapper;

    @PostMapping("/add")
    public ResponseEntity<ExamDTO> createExam(@RequestBody ExamCreateDto dto) {

        ExamSchedule schedule = examScheduleService.getScheduleById(dto.getExamScheduleId());
        Subject sub = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new EntityNotFoundException("Subject with given not found"));
        Exam savedExam = examService.createExam(examMapper.toEntity(dto, schedule, sub));
        return ResponseEntity.status(HttpStatus.CREATED).body(examMapper.toDto(savedExam));
    }

    @PostMapping("/addMultiple")
    public ResponseEntity<List<ExamDTO>> createExams(@RequestBody List<ExamCreateDto> dtos) {
        List<ExamDTO> examDTOs = new ArrayList<>();

        for (ExamCreateDto dto : dtos) {
            ExamSchedule schedule = examScheduleService.getScheduleById(dto.getExamScheduleId());
            Subject sub = subjectRepository.findById(dto.getSubjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Subject with given not found"));
            Exam savedExam = examService.createExam(examMapper.toEntity(dto, schedule, sub));
            examDTOs.add(examMapper.toDto(savedExam));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(examDTOs);
    }

    @GetMapping("/get")
    public ResponseEntity<List<ExamDTO>> getAllExams() {
        List<Exam> exams = examService.getAllExams();
        List<ExamDTO> dtos = exams.stream().map(examMapper::toDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ExamDTO> getExamById(@PathVariable long id) {
        Exam exam = examService.getExamById(id);
        return ResponseEntity.status(HttpStatus.OK).body(examMapper.toDto(exam));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExamById(@PathVariable long id) {
        examService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @GetMapping("/getScheduleExams/{id}")
    public ResponseEntity<List<ExamDTO>> getScheduleExams(@PathVariable Long id) {
        try {
            ExamSchedule examSchedule = examScheduleService.getScheduleById(id);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
        List<ExamDTO> dtos = examService.getScheduleExams(id).stream().map(examMapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ExamDTO>> getExamsByCourseId(@PathVariable Long courseId) {
        List<ExamDTO> examsDto = examService.getExamsByCourseId(courseId).stream().map(examMapper::toDto).toList();
        if (examsDto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(examsDto);
    }

    @GetMapping("/examType/{examType}")
    public ResponseEntity<List<ExamDTO>> getExamsByExamType(@PathVariable ExamTypeEnum examType) {
        List<ExamDTO> exams = examService.getExamsByExamType(examType).stream().map(examMapper::toDto).toList();
        if (exams.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/examStatus/{examStatus}")
    public ResponseEntity<List<ExamDTO>> getExamsByExamStatus(@PathVariable ExamStatusEnum examStatus) {
        List<ExamDTO> exams = examService.getExamsByExamStatus(examStatus).stream().map(examMapper::toDto).toList();
        if (exams.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ExamDTO>> getExamsForStudent(@PathVariable Long studentId) {
        List<ExamDTO> exams = examService.getExamsByStudentId(studentId);
        if (exams.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/student/{studentId}/exam-type/{examType}")
    public ResponseEntity<List<ExamDTO>> getExamsByStudentIdAndType(
            @PathVariable Long studentId,
            @PathVariable ExamTypeEnum examType) {
        return ResponseEntity.ok(examService.getExamsByStudentIdAndType(studentId, examType));
    }

    @GetMapping("/student/{studentId}/history")
    public ResponseEntity<List<ExamHistoryDTO>> getExamHistory(@PathVariable Long studentId) {
        return ResponseEntity.ok(examService.getExamHistory(studentId));
    }

    @GetMapping("/schedule/{scheduleId}/exam-type/{examType}")
    public ResponseEntity<List<ExamDTO>> getExamsByScheduleIdAndType(
            @PathVariable Long scheduleId,
            @PathVariable ExamTypeEnum examType) {

        List<Exam> exams = examService.getExamsByScheduleIdAndType(scheduleId, examType);
        if (exams.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ExamDTO> dtos = exams.stream().map(examMapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{examId}/status/{status}")
    public ResponseEntity<ExamDTO> updateExamStatus(
            @PathVariable long examId,
            @PathVariable ExamStatusEnum status) {

        ExamDTO updatedExam = examService.updateExamStatus(examId, status);

        if (updatedExam == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedExam);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<ExamDTO>> getUpcomingExams() {
        List<ExamDTO> upcomingExams = examService.getUpcomingExams();

        if (upcomingExams.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(upcomingExams);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<ExamDTO>> getCompletedExams() {
        List<ExamDTO> completedExams = examService.getCompletedExams();

        if (completedExams.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(completedExams);
    }

    @PutMapping("/{examId}/complete")
    public ResponseEntity<String> markExamCompletedAndStudentsPresent(
            @PathVariable Long examId,
            @RequestBody List<Long> studentIds) {

        boolean result = examService.markExamAsCompletedAndStudentsPresent(examId, studentIds);

        if (result) {
            return ResponseEntity.ok("Exam marked as completed and students marked as present.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Exam or Students not found.");
        }
    }

    @PutMapping("/update-status")
    public ResponseEntity<List<ExamDTO>> updateExamStatus() {

        return ResponseEntity.ok(examService.updateExamStatusIfExpired());
    }
}
