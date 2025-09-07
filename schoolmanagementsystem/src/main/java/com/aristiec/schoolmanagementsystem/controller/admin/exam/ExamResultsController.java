package com.aristiec.schoolmanagementsystem.controller.admin.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamDTO;
import com.aristiec.schoolmanagementsystem.dto.exam.StudentResultDTO;
import com.aristiec.schoolmanagementsystem.service.exam.ExamResultsService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exam-results")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Exam Results Management", description = "Manage exam results for students")
public class ExamResultsController {

    @Autowired
    private ExamResultsService resultsService;

    @GetMapping
    public ResponseEntity<List<StudentResultDTO>> getStudentResults(
            @RequestParam Long studentId,
            @RequestParam Integer semester,
            @RequestParam ExamTypeEnum examType
    ) {
        List<StudentResultDTO> results = resultsService.getStudentResults(studentId, semester, examType);
        return ResponseEntity.ok(results);
    }
    @GetMapping("/student/{studentId}/absent/{sem}")
    public List<ExamDTO> getExamsAbsentForStudent(@PathVariable Long studentId,@PathVariable int sem) {
        return resultsService.getExamsAbsentForStudent(studentId, sem);
    }
}
