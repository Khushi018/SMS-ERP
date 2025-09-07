package com.aristiec.schoolmanagementsystem.controller.parentDashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.AssignmentDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.ParentDashboardDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.SubjectAttendanceDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.TotalAttendanceDTO;
import com.aristiec.schoolmanagementsystem.service.parentDashboard.ParentDashboardService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/parent-dashboard")

@SecurityRequirement(name = "bearerAuth") 
public class ParentDashboardController {
     
    @Autowired
    private ParentDashboardService parentDashboardService;
   
     @GetMapping("/{parentId}/student/{studentId}")
    public ResponseEntity<ParentDashboardDTO>StudentOverview(@PathVariable Long parentId,@PathVariable Long studentId){

        ParentDashboardDTO dashboard=parentDashboardService.getParentDashboardDTO(parentId, studentId);

        return ResponseEntity.ok(dashboard);
    }

       @GetMapping("/total-attendance/{studentId}")
    public ResponseEntity<TotalAttendanceDTO> getTotalAttendance(@PathVariable Long studentId) {
        TotalAttendanceDTO totalAttendance = parentDashboardService.getTotalAttendanceByStudent(studentId);
        return ResponseEntity.ok(totalAttendance);
    }

    @GetMapping("/subject-attendance/{studentId}")
    public ResponseEntity<List<SubjectAttendanceDTO>> getSubjectAttendance(@PathVariable Long studentId) {
        List<SubjectAttendanceDTO> subjectAttendanceList = parentDashboardService.getSubjectAttendanceByStudent(studentId);
        return ResponseEntity.ok(subjectAttendanceList);
    }


       @GetMapping("/{parentId}/student/{studentId}/assignments")
     public ResponseEntity<List<AssignmentDTO>>getAssignments(@PathVariable Long parentId, @PathVariable Long studentId){
      
      List<AssignmentDTO>assignments=parentDashboardService.getAssignmentsForStudent(parentId, studentId);
        return ResponseEntity.ok(assignments);
     }

}
