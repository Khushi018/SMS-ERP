package com.aristiec.schoolmanagementsystem.controller.onlineexam;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;

import com.aristiec.schoolmanagementsystem.dto.onlineexam.ExamContentDto;
import com.aristiec.schoolmanagementsystem.dto.onlineexam.ExamSubmissionDto;
import com.aristiec.schoolmanagementsystem.dto.onlineexam.GradeDto;
import com.aristiec.schoolmanagementsystem.dto.onlineexam.OnlineExaminationDto;
import com.aristiec.schoolmanagementsystem.dto.onlineexam.SubmitExamDto;
import com.aristiec.schoolmanagementsystem.dto.onlineexam.AnswerDto;
import com.aristiec.schoolmanagementsystem.exception.EntityNotFoundException;
import com.aristiec.schoolmanagementsystem.service.onlineexam.OnlineExamService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/onlineexams")
@SecurityRequirement(name = "bearerAuth")
public class OnlineExamController {

    @Autowired
    private OnlineExamService examService;

    @Autowired
    private StudentDetailsRepository studentDetailsRepository;

    @PostMapping
    public ResponseEntity<?> createExam(@RequestBody OnlineExaminationDto dto) {
        try {
            return ResponseEntity.ok(examService.createExam(dto));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.ok(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.ok(Map.of("message", "Failed to create exam"));
        }
    }

    @GetMapping("/by-course-and-semester")
    public ResponseEntity<?> getExamsByCourseAndSemester(
            @RequestParam Long courseId,
            @RequestParam Integer semester) {
        try {
            return ResponseEntity.ok(examService.getExamsByCourseAndSemester(courseId, semester));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to fetch exams"));
        }
    }

    @GetMapping("/{examId}/questions")
    public ResponseEntity<?> getOnlyContentsByExamId(@PathVariable Long examId) {
        try {
            List<ExamContentDto> contents = examService.getOnlyContentsByExamId(examId);
            return ResponseEntity.ok(contents);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to fetch contents"));
        }
    }

    @GetMapping("/{examId}/questions-with-answers")
    public ResponseEntity<?> getContentsWithAnswers(@PathVariable Long examId) {
        try {
            List<ExamContentDto> contents = examService.getContentsWithAnswersByExamId(examId);
            return ResponseEntity.ok(contents);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to fetch contents with answers"));
        }
    }

    @PostMapping("/{examId}/questions")
    public ResponseEntity<?> addContent(
            @PathVariable Long examId,
            @RequestBody ExamContentDto dto) {
        try {
            return ResponseEntity.ok(examService.addContent(examId, dto));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.ok(Map.of("message", "Exam not found with id " + examId));
        } catch (Exception ex) {
            return ResponseEntity.ok(Map.of("message", "Failed to add question"));
        }
    }

    @GetMapping("/available")
    @Operation(summary = "Get all exams with optional filters (courseid, subjectid, date range)")
    public ResponseEntity<?> listExams(
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            return ResponseEntity.ok(examService.getAllExams(courseId, subjectId, startDate, endDate));
        } catch (Exception ex) {
            return ResponseEntity.ok(Map.of("message", "Failed to fetch exams"));
        }
    }

    @GetMapping("/student-view")
    @Operation(summary = "Get all exams visible to a specific student with submission status")
    public ResponseEntity<?> studentViewExams(@RequestParam Long studentId) {
        try {
            return ResponseEntity.ok(examService.getAllExamsForStudent(studentId));
        } catch (Exception ex) {
            ex.printStackTrace(); // ← Add this line to see the real error in console
            return ResponseEntity.ok(Map.of("message", "Failed to fetch student exams"));
        }
    }

    @GetMapping("/{examId}")
    public ResponseEntity<?> getExam(@PathVariable Long examId) {
        try {
            return ResponseEntity.ok(examService.getExamById(examId));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.ok(Map.of("message", "Exam not found with id " + examId));
        } catch (Exception ex) {
            return ResponseEntity.ok(Map.of("message", "Failed to fetch exam"));
        }
    }

    @PostMapping("/{examId}/submit")
    public ResponseEntity<?> submit(
            @PathVariable Long examId,
            @RequestBody SubmitExamDto dto) {
        try {
            ExamSubmissionDto result = examService.submitExam(examId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.ok(Map.of("message", ex.getMessage())); // already has "with id ..."
        } catch (Exception ex) {
            return ResponseEntity.ok(Map.of("message", "Failed to submit exam"));
        }
    }

    @GetMapping("/submission/{id}")
    public ResponseEntity<?> getSubmission(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(examService.getSubmission(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.ok(Map.of("message", "Submission not found with id " + id));
        } catch (Exception ex) {
            return ResponseEntity.ok(Map.of("message", "Failed to fetch submission"));
        }
    }

    @GetMapping("/submissions")
    public ResponseEntity<?> listSubmissions(
            @RequestParam(required = false) Long examId,
            @RequestParam(required = false) Long studentId) {
        try {
            List<ExamSubmissionDto> list = examService.listSubmissions(examId, studentId);
            if (list.isEmpty()) {
                return ResponseEntity.ok(Map.of("message", "No submissions found"));
            }
            return ResponseEntity.ok(list);
        } catch (Exception ex) {
            return ResponseEntity.ok(Map.of("message", "Failed to fetch submissions"));
        }
    }

    @PutMapping("/{examId}/submissions/{submissionId}/grade")
    public ResponseEntity<?> grade(
            @PathVariable Long examId,
            @PathVariable Long submissionId,
            @RequestBody GradeDto grade) {
        try {
            ExamSubmissionDto result = examService.gradeSubmission(examId, submissionId, grade);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.ok(Map.of("message", ex.getMessage())); // already has exact msg
        } catch (Exception ex) {
            return ResponseEntity.ok(Map.of("message", "Failed to grade submission"));
        }
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<?> deleteAllExams() {
        try {
            examService.deleteAllExams();
            return ResponseEntity.ok(Map.of("message", "All exams deleted successfully"));
        } catch (Exception ex) {
            return ResponseEntity.ok(Map.of("message", "Failed to delete exams"));
        }
    }

    @Operation(summary = "Submit descriptive answers with PDFs")
    @PostMapping(value = "/{examId}/submit-descriptive", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submitWithPdf(
            @PathVariable Long examId,
            @RequestPart("dto") SubmitExamDto dto,
            @RequestPart("files") List<MultipartFile> files) {
        try {
            // Fetch student from DB
            StudentDetails student = studentDetailsRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Invalid student ID"));

            String studentCode = student.getStudentCode(); // This will be like "CS202407"

            for (AnswerDto answer : dto.getAnswers()) {
                String matchFile = answer.getFileName(); // from JSON

                for (MultipartFile file : files) {
                    if (file.getOriginalFilename().equals(matchFile)) {
                        String storedName = studentCode + "_" + UUID.randomUUID() + "_" + matchFile;
                        Path path = Paths.get("uploads/exams/", storedName);
                        Files.createDirectories(path.getParent());
                        Files.write(path, file.getBytes());

                        answer.setPdfUrl(path.toString()); // ✅ link PDF to the answer
                        break;
                    }
                }
            }

            ExamSubmissionDto result = examService.submitExam(examId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Submission failed"));
        }
    }

    @GetMapping("/pdf")
    public ResponseEntity<UrlResource> downloadPdf(@RequestParam String filename) {
        try {
            Path filePath = Paths.get("uploads/exams/").resolve(filename).normalize();
            UrlResource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("File not found");
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
