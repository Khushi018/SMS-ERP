package com.aristiec.schoolmanagementsystem.serviceImpl.assignment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.aristiec.schoolmanagementsystem.constant.enums.AssignmentStatusEnum;
import com.aristiec.schoolmanagementsystem.dto.assignment.AssignmentSubmissionDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.assignment.Assignment;
import com.aristiec.schoolmanagementsystem.modal.assignment.AssignmentSubmission;
import com.aristiec.schoolmanagementsystem.repository.assignment.AssignmentRepository;
import com.aristiec.schoolmanagementsystem.repository.assignment.AssignmentSubmissionRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.service.assignment.AssignmentSubmissionService;

@Service
public class AssignmentSubmissionServiceIMPl implements AssignmentSubmissionService {

   @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private StudentDetailsRepository studentRepository;

    @Autowired
    private AssignmentSubmissionRepository submissionRepository;

    @Autowired
    private ModelMapper modelMapper;

     @Override
    public AssignmentSubmissionDTO submitOffline(Long assignmentId, Long studentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        StudentDetails student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        AssignmentSubmission submission = new AssignmentSubmission();
        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setAttempted(true);
        submission.setAnswer(null);
        submission.setSubmittedAt(LocalDateTime.now());
        submission.setSubmittedLate(LocalDate.now().isAfter(assignment.getDueDate()));
        submission.setStatus(AssignmentStatusEnum.SUBMITTED);

        return modelMapper.map(submissionRepository.save(submission), AssignmentSubmissionDTO.class);

    }

    @Override
    public AssignmentSubmissionDTO submitOnline(Long assignmentId, Long studentId, String answer, MultipartFile file) {
          boolean hasAnswer = answer != null && !answer.trim().isEmpty();
        boolean hasFile = file != null && !file.isEmpty();

         if (hasAnswer == hasFile) {
            throw new RuntimeException("Submit either a descriptive answer or a file, not both.");
        }

       Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        StudentDetails student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        AssignmentSubmission submission = new AssignmentSubmission();
        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setAttempted(true);
        submission.setSubmittedAt(LocalDateTime.now());
        submission.setSubmittedLate(LocalDate.now().isAfter(assignment.getDueDate()));
            if (hasAnswer) {
            submission.setAnswer(answer);
            submission.setFileData(null);
        } else {
            try {
                submission.setFileData(file.getBytes());
                submission.setAnswer(null);
            } catch (Exception e) {
                throw new RuntimeException("File read failed", e);
            }
        }
            submission.setStatus(AssignmentStatusEnum.SUBMITTED);

        
        return modelMapper.map(submissionRepository.save(submission), AssignmentSubmissionDTO.class);
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(Long submissionId) {
               AssignmentSubmission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Submission not found"));

        if (submission.getFileData() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No file submitted for this assignment.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("submission_" + submissionId + ".bin") 
                .build());

        return new ResponseEntity<>(submission.getFileData(), headers, HttpStatus.OK); 

   }
    
}
