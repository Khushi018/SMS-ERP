package com.aristiec.schoolmanagementsystem.controller.leave;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.constant.enums.DesignationEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.LeaveReasonEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.LeaveStatus;
import com.aristiec.schoolmanagementsystem.dto.leave.LeaveDTO;
import com.aristiec.schoolmanagementsystem.dto.leave.LeaveResponseDTO;
import com.aristiec.schoolmanagementsystem.service.leave.LeaveService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/leaves")
@SecurityRequirement(name = "bearerAuth") 
@Tag(name = "Leave Management", description = "Manage leave applications for students and faculty/staff")
public class LeaveController {
      @Autowired
    private LeaveService leaveService;
    
    @PostMapping
    public ResponseEntity<String> applyLeave(@RequestBody LeaveDTO dto) {
        return ResponseEntity.ok(leaveService.applyLeave(dto));
    }

    @GetMapping("/my-leaves")
    public ResponseEntity<List<LeaveResponseDTO>> getMyLeaves(@RequestParam Long userId,
                                                              @RequestParam DesignationEnum designation) {
        return ResponseEntity.ok(leaveService.getMyLeaves(userId, designation));
    }

    @GetMapping("/student-leaves")
    public ResponseEntity<List<LeaveResponseDTO>> getStudentLeaves() {
        return ResponseEntity.ok(leaveService.getStudentLeaves());
    }

    @PutMapping("/student-leaves/status/{id}")
    public ResponseEntity<String> updateStudentLeaveStatus(@PathVariable Long id,
                                                           @RequestParam LeaveStatus status) {
        return ResponseEntity.ok(leaveService.updateStudentLeaveStatus(id, status));
    }

    @GetMapping("/admin/faculty-staff-leaves")
    public ResponseEntity<List<LeaveResponseDTO>> getFacultyAndStaffLeaves() {
        return ResponseEntity.ok(leaveService.getFacultyAndStaffLeaves());
    }

    @PutMapping("/admin/status/{id}")
    public ResponseEntity<String> updateFacultyStaffLeave(@PathVariable Long id,
                                                          @RequestParam LeaveStatus status) {
        return ResponseEntity.ok(leaveService.updateFacultyOrStaffLeaveStatus(id, status));
    }


      @GetMapping("admin/designation")
    public ResponseEntity<List<LeaveResponseDTO>> getFacultyOrStaffLeavesByDesignation(@RequestParam DesignationEnum designation) {
        return ResponseEntity.ok(leaveService.getFacultyOrStaffLeavesByDesignation(designation));
    }

    @GetMapping("admin/status")
    public ResponseEntity<List<LeaveResponseDTO>>getFacultyOrStaffLeavesByStatus(@RequestParam LeaveStatus status) {
        return ResponseEntity.ok(leaveService.getFacultyOrStaffLeavesByStatus(status));
    }

    @GetMapping("admin/reason")
    public ResponseEntity<List<LeaveResponseDTO>>getFacultyOrStaffLeavesByReason(@RequestParam LeaveReasonEnum reason) {
        return ResponseEntity.ok(leaveService.getFacultyOrStaffLeavesByReason(reason));
    }

    @GetMapping("admin/applied-date")
    public ResponseEntity<List<LeaveResponseDTO>>getFacultyOrStaffLeavesByAppliedDate(@RequestParam String date) {
        return ResponseEntity.ok(leaveService.getFacultyOrStaffLeavesByAppliedDate(LocalDate.parse(date)));
    }

    @GetMapping("admin/status-designation")
    public ResponseEntity<List<LeaveResponseDTO>>getFacultyOrStaffLeavesByStatusAndDesignation(@RequestParam LeaveStatus status,
                                                                              @RequestParam DesignationEnum designation) {
        return ResponseEntity.ok(leaveService.getFacultyOrStaffLeavesByStatusAndDesignation(status, designation));
    }

     @GetMapping("faculty/status")
    public ResponseEntity<List<LeaveResponseDTO>> getStudentLeavesByStatus(@RequestParam LeaveStatus status) {
        return ResponseEntity.ok(leaveService.getStudentLeavesByStatus(status));
    }

    @GetMapping("faculty/reason")
    public ResponseEntity<List<LeaveResponseDTO>> getStudentLeavesByReason(@RequestParam LeaveReasonEnum reason) {
        return ResponseEntity.ok(leaveService.getStudentLeavesByReason(reason));
    }

    @GetMapping("faculty/applied-date")
    public ResponseEntity<List<LeaveResponseDTO>> getStudentLeavesByAppliedDate(@RequestParam String appliedDate) {
        return ResponseEntity.ok(leaveService.getStudentLeavesByAppliedDate(LocalDate.parse(appliedDate)));
    }

    @GetMapping("faculty/status-designation")
    public ResponseEntity<List<LeaveResponseDTO>> getStudentLeavesByStatusAndDesignation(@RequestParam LeaveStatus status,
                                                                                         @RequestParam DesignationEnum designation) {
        return ResponseEntity.ok(leaveService.getStudentLeavesByStatusAndDesignation(status, designation));
    }

    
}
