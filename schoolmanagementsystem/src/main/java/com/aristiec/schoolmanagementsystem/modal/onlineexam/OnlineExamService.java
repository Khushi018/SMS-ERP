package com.aristiec.schoolmanagementsystem.modal.onlineexam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aristiec.schoolmanagementsystem.modal.onlineexam.repo.ExamSessionRepository;
import com.aristiec.schoolmanagementsystem.modal.onlineexam.repo.OnExamRepository;
import com.aristiec.schoolmanagementsystem.modal.onlineexam.repo.SnapshotRepository;
import com.aristiec.schoolmanagementsystem.modal.onlineexam.repo.StudentRepository;
import com.aristiec.schoolmanagementsystem.modal.onlineexam.repo.ViolationRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OnlineExamService {
    private final StudentRepository studentRepo;
    private final OnExamRepository examRepo;
    private final ExamSessionRepository sessionRepo;
    private final SnapshotRepository snapshotRepo;
    private final ViolationRepository violationRepo;

    public void startExam(Long studentId, Long examId) {
        sessionRepo.save(new ExamSession(null, studentId, examId, LocalDateTime.now(), null));
    }

    public void submitExam(Long studentId, Long examId) {
        ExamSession session = sessionRepo.findByStudentIdAndExamId(studentId, examId).orElseThrow();
        session.setEndedAt(LocalDateTime.now());
        sessionRepo.save(session);
    }

    public void saveSnapshot(Long studentId, MultipartFile file) {
        try {
            String fileName = "snapshots/student_" + studentId + "_" + System.currentTimeMillis() + ".jpg";
           File targetFile = new File(fileName);
targetFile.getParentFile().mkdirs(); // Create parent directories if not exist
file.transferTo(targetFile);
            snapshotRepo.save(new WebcamSnapshot(null, studentId, fileName, LocalDateTime.now(),8));
        } catch (IOException e) {
            throw new RuntimeException("Snapshot upload failed", e);
        }
    }

    public void logViolation(Long studentId, String type) {
        violationRepo.save(new ExamViolation(null, studentId, type, LocalDateTime.now(), null));
    }

    public List<ExamViolation> getViolations(Long studentId) {
        return violationRepo.findByStudentId(studentId);
    }

    public Student addStudent(Student student) {
        return studentRepo.save(student);
    }

    public OnlineExam addExam(OnlineExam exam) {
        return examRepo.save(exam);
    }
}