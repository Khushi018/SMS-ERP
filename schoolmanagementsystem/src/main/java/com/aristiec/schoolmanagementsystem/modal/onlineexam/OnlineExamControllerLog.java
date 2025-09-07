package com.aristiec.schoolmanagementsystem.modal.onlineexam;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ai.djl.modality.cv.output.DetectedObjects;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/v1/exams")
@RequiredArgsConstructor
public class OnlineExamControllerLog {
    private final OnlineExamService examService;

    @Autowired
    private ViolationLogRepository repository;

    @PostMapping("/violations")
    public ResponseEntity<String> logViolation(@RequestBody ViolationLog violation) {
        violation.setTimestamp(LocalDateTime.now());
        repository.save(violation);
        return ResponseEntity.ok("Violation logged successfully");
    }

    @PostMapping("/{studentId}/{examId}/start")
    public ResponseEntity<String> start(@PathVariable Long studentId, @PathVariable Long examId) {
        examService.startExam(studentId, examId);
        return ResponseEntity.ok("Exam started");
    }

    @PostMapping("/{studentId}/{examId}/submit")
    public ResponseEntity<String> submit(@PathVariable Long studentId, @PathVariable Long examId) {
        examService.submitExam(studentId, examId);
        return ResponseEntity.ok("Exam submitted");
    }

    @PostMapping("/{studentId}/snapshot")
    public ResponseEntity<String> upload(@PathVariable Long studentId, @RequestParam MultipartFile snapshot) {
        examService.saveSnapshot(studentId, snapshot);
        return ResponseEntity.ok("Snapshot saved");
    }

    @PostMapping("/{studentId}/violation")
    public ResponseEntity<String> violation(@PathVariable Long studentId, @RequestParam String type) {
        examService.logViolation(studentId, type);
        return ResponseEntity.ok("Violation logged");
    }

    @GetMapping("/{studentId}/violations")
    public ResponseEntity<List<ExamViolation>> getViolations(@PathVariable Long studentId) {
        return ResponseEntity.ok(examService.getViolations(studentId));
    }

    @PostMapping("/add-student")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(examService.addStudent(student));
    }

    @PostMapping("/add-exam")
    public ResponseEntity<OnlineExam> addExam(@RequestBody OnlineExam exam) {
        return ResponseEntity.ok(examService.addExam(exam));
    }

    @PostMapping("/snapshot")
    public ResponseEntity<?> handleSnapshot(@RequestBody SnapshotDTO snapshot) throws Exception {
        // Decode Base64 image to file
        byte[] decoded = Base64.getDecoder().decode(snapshot.getImage().split(",")[1]);
        Path tempFile = Files.createTempFile("snapshot", ".jpg");
        Files.write(tempFile, decoded);

        FaceDetectionService faceDetectionService = new FaceDetectionService();
        DetectedObjects results = faceDetectionService.detectFaces(tempFile);

        long faceCount = results.getNumberOfObjects();

        if (faceCount > 1) {
            // log violation
        }
        return ResponseEntity.ok("Detected " + faceCount + " face(s)");
    }
    
}
