package com.aristiec.schoolmanagementsystem.controller.admin.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.GradeEnum;
import com.aristiec.schoolmanagementsystem.dto.exam.*;
import com.aristiec.schoolmanagementsystem.service.exam.MarksService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marks")
@SecurityRequirement(name = "bearerAuth") 
@Tag(name = "Marks Management", description = "Manage marks and grades for students in exams")
public class MarksController {

    @Autowired
    private MarksService marksService;

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<MarksReturnDto>> getMarksForExam(@PathVariable long examId) {
        List<MarksReturnDto> marks = marksService.getMarksForExam(examId);
        return ResponseEntity.ok(marks);
    }

    @GetMapping("/student/{studentId}/exam/{examId}")
    public ResponseEntity<MarksReturnDto> getMarksForStudentAndExam(@PathVariable Long studentId, @PathVariable long examId) {
        MarksReturnDto marks = marksService.getMarksForStudentAndExam(studentId, examId);
        return ResponseEntity.ok(marks);
    }

    @GetMapping("/grade/{grade}")
    public ResponseEntity<List<Long>> getStudentWithGrade(@PathVariable GradeEnum grade){
        return ResponseEntity.ok(marksService.getStudentWithGrade(grade));
    }

    @PostMapping
    public ResponseEntity<MarksReturnDto> saveMarksForStudent(@RequestBody MarksDTO marksDTO) {
        MarksReturnDto marks = marksService.saveMarksForStudent(marksDTO.getStudentId(), marksDTO.getExamId(), marksDTO.getMarksObtained(), marksDTO.getGrade());
        return ResponseEntity.ok(marks);
    }

    @DeleteMapping("/{marksId}")
    public ResponseEntity<Void> deleteMarksById(@PathVariable Long marksId) {
        marksService.deleteMarksById(marksId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{marksId}/{grade}/{marksObtained}")
    public ResponseEntity<MarksReturnDto> updateMarksAndGrade(@PathVariable Long marksId,
                                                        @PathVariable GradeEnum grade,
                                                        @PathVariable int marksObtained) {
        MarksReturnDto updatedMarks = marksService.updateMarksAndGrade(marksId, grade, marksObtained);
        return ResponseEntity.ok(updatedMarks);
    }

    @GetMapping("/student/{studentId}/examSchedule/{schId}")
    public ResponseEntity<List<MarksReturnDto>> getStudentMarksBySchId(@PathVariable Long studentId, @PathVariable long schId){
        List<MarksReturnDto> marks = marksService.getMarksForStudentAndExamSch(studentId, schId);

        return ResponseEntity.ok(marks);
    }


 
}
