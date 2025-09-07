package com.aristiec.schoolmanagementsystem.controller.admin.hostel;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.hostel.HostelIMPStaffDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.HostelStaffDTO;
import com.aristiec.schoolmanagementsystem.service.hostel.HostelStaffService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/hostel-staff")

@SecurityRequirement(name = "bearerAuth") 
public class HostelStaffController {
    private final HostelStaffService staffService;

    public HostelStaffController(HostelStaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping("/add")
    public ResponseEntity<HostelStaffDTO> create(@RequestBody HostelStaffDTO dto) {
        return ResponseEntity.ok(staffService.addStaff(dto));
    }

    @GetMapping("/by-hostel/{hostelId}")
    public ResponseEntity<List<HostelStaffDTO>> getAll(@PathVariable Long hostelId) {
        return ResponseEntity.ok(staffService.getAllByHostelId(hostelId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HostelStaffDTO> update(@PathVariable Long id, @RequestBody HostelStaffDTO dto) {
        return ResponseEntity.ok(staffService.updateStaff(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.ok("Deleted hostel staff with ID: " + id);
    }

    @GetMapping("/important/{studentId}")
public ResponseEntity<List<HostelIMPStaffDTO>> getImportantStaffByStudent(@PathVariable Long studentId) {
    return ResponseEntity.ok(staffService.getImportantStaffByStudentId(studentId));
}


}
