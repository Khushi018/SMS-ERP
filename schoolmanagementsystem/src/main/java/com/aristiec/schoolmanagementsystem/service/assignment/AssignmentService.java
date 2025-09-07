package com.aristiec.schoolmanagementsystem.service.assignment;

import java.time.LocalDate;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aristiec.schoolmanagementsystem.dto.AssignmentDTO;
import com.aristiec.schoolmanagementsystem.dto.assignment.AssignmentRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.assignment.AssignmentSubmissionStatsDTO;
import com.aristiec.schoolmanagementsystem.dto.assignment.AssignmentSummaryDTO;
import com.aristiec.schoolmanagementsystem.dto.subjectAssignment.AssiDTO;
import com.aristiec.schoolmanagementsystem.dto.subjectAssignment.SubjectAssiDTO;
import com.aristiec.schoolmanagementsystem.modal.assignment.Assignment;


public interface AssignmentService {
 
     AssiDTO createAssignment(AssignmentRequestDTO requestDTO);
    AssiDTO getAssignmentById(Long id);
    Page<AssiDTO> getAllAssignments(Pageable pageable);
    AssiDTO updateAssignment(Long id, AssignmentRequestDTO requestDTO);
    void deleteAssignment(Long id);
    List<SubjectAssiDTO> getAllAssignmentsSubjectWise();
    List<AssiDTO> getAssignmentsBySubjectName(String subjectName);
    AssignmentDTO markAttempted(Long assignmentId, Boolean attempted);
     List<AssiDTO> getAssignmentsBySubjectAndStudent(Long subjectId, Long studentId);
      List<AssiDTO> getOverdueAssignments();
      List<AssiDTO> getAssignmentsBySubjectAndAttemptStatus(Long subjectId, Long studentId, boolean attempted);
      List<AssiDTO> getAllCompletedAssignmentsByStudent(Long studentId);
      List<AssiDTO> getAllIncompleteAssignmentsByStudent(Long studentId);
      List<AssiDTO> getOverdueAssignmentsByStudent(Long studentId);
      AssignmentSummaryDTO getOverallAssignmentSummary();
      AssignmentSubmissionStatsDTO getSubmissionStats(Long studentId);
      List<AssiDTO> getAssignmentsByDueDate(LocalDate dueDate);
      Page<AssiDTO> getByCourseIdAndSem(Long courseId, Integer sem, Pageable pageable);






    
} 
