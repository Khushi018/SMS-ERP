package com.aristiec.schoolmanagementsystem.controller.admin.details;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.dto.ParentDetailsDto;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.CourseExamMarksDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.ExamScheduleDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.SubjectMarksDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.SubjectWiseComparisonDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aristiec.schoolmanagementsystem.service.details.ParentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
//@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/v1/parent")
@Tag(name = "Parent Details", description = "Get parent details of a student")

@SecurityRequirement(name = "bearerAuth") 
public class ParentDetailsController {

    @Autowired
    private ParentService parentService;

    @GetMapping("/{studentId}")
    @Operation(summary = "Get parent details by student ID", description = "Retrieves the parent's information for a given student ID")
    public ResponseEntity<ParentDetailsDto> getParentDetailsByStudentId(
            @Parameter(description = "ID of the student whose parent's details are to be fetched") @PathVariable Long studentId) {

        ParentDetailsDto parentDetails = parentService.getParentDetailsByStudentId(studentId);
        return ResponseEntity.ok(parentDetails);
    }


    @GetMapping("/parentId/{parentId}")
    @Operation(summary = "Get parent details by parent ID", description = "Retrieves the parent's information for a given parent ID")
    public ResponseEntity<ParentDetailsDto> getParentDetailsByParentId(
            @Parameter(description = "ID of the parent whose details are to be fetched") @PathVariable Long parentId) {

        ParentDetailsDto parentDetails = parentService.getParentDetailsByParentId(parentId);
        return ResponseEntity.ok(parentDetails);
    }
    @DeleteMapping("/{parentId}")
    @Operation(summary = "Delete parent details by parent ID", description = "Deletes the parent's information for a given parent ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parent details deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Parent details not found", content = @Content)
    })
    public ResponseEntity<Void> deleteParentDetailsById(
            @Parameter(description = "ID of the parent to delete") @PathVariable Long parentId) {

        parentService.deleteParentDetailsById(parentId);
        return ResponseEntity.ok().build();
    }

     @GetMapping("/by-email")
    public ResponseEntity<ParentDetailsDto> getParentByEmail(@RequestParam String email) {
       ParentDetailsDto parentDetails = parentService.getParentDetailsByEmail(email);
        return ResponseEntity.ok(parentDetails);
    }




 @GetMapping("/{parentId}/exam-schedule")
    public ResponseEntity<List<ExamScheduleDTO>> getExamSchedule(@PathVariable Long parentId) {
        List<ExamScheduleDTO> schedule = parentService.getExamScheduleForParent(parentId);
        return ResponseEntity.ok(schedule);
    }

   


   
}
