package com.aristiec.schoolmanagementsystem.controller.admin.hostel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.hostel.MaintenanceRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.MaintenanceRequestResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.MaintenanceStatusUpdateDTO;
import com.aristiec.schoolmanagementsystem.service.hostel.MaintenanceRequestService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/maintenance-request")
@SecurityRequirement(name = "bearerAuth") 
public class MaintenanceRequestController {
    @Autowired
    private MaintenanceRequestService maintenanceRequestService;

      @PostMapping("/submit/{studentId}")
    public ResponseEntity<String> submitMaintenanceRequest(
            @PathVariable Long studentId,
            @RequestBody MaintenanceRequestDTO dto) {

        String msg = maintenanceRequestService.submitRequest(studentId, dto);
        return ResponseEntity.ok(msg);
    }

    @PostMapping("/update-status")
public ResponseEntity<String> updateMaintenanceStatus(@RequestBody MaintenanceStatusUpdateDTO dto) {
    String msg = maintenanceRequestService.updateStatus(dto);
    return ResponseEntity.ok(msg);
}

@GetMapping("/student/prev-requests")
public ResponseEntity<List<MaintenanceRequestResponseDTO>> getAllRequestsByStudent(
        @RequestParam Long studentId) {
    return ResponseEntity.ok(maintenanceRequestService.getStudentRequestHistory(studentId));
}

}
