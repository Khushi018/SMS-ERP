package com.aristiec.schoolmanagementsystem.transport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.transport.dto.StudentStoppageDropOffDto;
import com.aristiec.schoolmanagementsystem.transport.dto.StudentTransportAssignmentDto;
import com.aristiec.schoolmanagementsystem.transport.service.StudentStoppageDropOffService;
import com.aristiec.schoolmanagementsystem.transport.service.StudentTransportAssignmentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/transport/assignments")
@Tag(name = "Student Transport Assignment Management", description = "Operations related to managing student transport assignments.")
public class StudentTransportAssignmentController {

    @Autowired
    private StudentTransportAssignmentService service;

    @Autowired
    private StudentStoppageDropOffService stoppageDropOffService;

    @PostMapping("/assign")
    public ResponseEntity<StudentTransportAssignmentDto> assign(@RequestBody StudentTransportAssignmentDto dto) {
        return ResponseEntity.status(201).body(service.assign(dto));
    }

    @PostMapping("/assign-dropoff/{assignmentId}")
    public ResponseEntity<StudentStoppageDropOffDto> assignDropOff(
            @PathVariable Long assignmentId,
            @RequestBody StudentStoppageDropOffDto dto) {
        return ResponseEntity.ok(stoppageDropOffService.assignDropOff(assignmentId, dto));
    }

    @GetMapping("/route/{routeId}")
    public ResponseEntity<List<StudentTransportAssignmentDto>> byRoute(@PathVariable Long routeId) {
        return ResponseEntity.ok(service.getByRoute(routeId));
    }

    @GetMapping("/student/{studentCode}")
    public ResponseEntity<List<StudentTransportAssignmentDto>> byStudent(@PathVariable String studentCode) {
        return ResponseEntity.ok(service.getByStudentCode(studentCode));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }
}