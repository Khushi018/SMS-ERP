package com.aristiec.schoolmanagementsystem.service.leave;

import java.time.LocalDate;
import java.util.List;

import com.aristiec.schoolmanagementsystem.constant.enums.DesignationEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.LeaveReasonEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.LeaveStatus;
import com.aristiec.schoolmanagementsystem.dto.leave.LeaveDTO;
import com.aristiec.schoolmanagementsystem.dto.leave.LeaveResponseDTO;

public interface LeaveService {
     String applyLeave(LeaveDTO dto);
     List<LeaveResponseDTO> getMyLeaves(Long userId, DesignationEnum designation);
   
     // admin Operations
    List<LeaveResponseDTO> getFacultyAndStaffLeaves();
    String updateFacultyOrStaffLeaveStatus(Long id, LeaveStatus status);
List<LeaveResponseDTO> getFacultyOrStaffLeavesByDesignation(DesignationEnum designation);
List<LeaveResponseDTO> getFacultyOrStaffLeavesByStatus(LeaveStatus status);
List<LeaveResponseDTO> getFacultyOrStaffLeavesByReason(LeaveReasonEnum reason);
List<LeaveResponseDTO> getFacultyOrStaffLeavesByAppliedDate(LocalDate date);
List<LeaveResponseDTO> getFacultyOrStaffLeavesByStatusAndDesignation(LeaveStatus status, DesignationEnum designation);

  // Faculties Operations 
     List<LeaveResponseDTO> getStudentLeaves();
     String updateStudentLeaveStatus(Long id, LeaveStatus status);
List<LeaveResponseDTO> getStudentLeavesByStatus(LeaveStatus status);
List<LeaveResponseDTO> getStudentLeavesByReason(LeaveReasonEnum reason);
List<LeaveResponseDTO> getStudentLeavesByAppliedDate(LocalDate appliedDate);
List<LeaveResponseDTO> getStudentLeavesByStatusAndDesignation(LeaveStatus status, DesignationEnum designation);




}
