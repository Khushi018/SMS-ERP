package com.aristiec.schoolmanagementsystem.controller.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.notification.ExamNotificationRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.notification.ExamNotificationResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.notification.NotificationResponseDTO;
import com.aristiec.schoolmanagementsystem.service.notification.ExamNotificationService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/exam-notifications")
@SecurityRequirement(name = "bearerAuth")
public class ExamNotificationController {
    @Autowired
    private ExamNotificationService service;

    @PostMapping
    public ResponseEntity<ExamNotificationResponseDTO> create(@RequestBody ExamNotificationRequestDTO dto) {
        return ResponseEntity.ok(service.createNotification(dto));
    }

    @GetMapping
    public ResponseEntity<List<ExamNotificationResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllNotifications());
    }



    @GetMapping("/course/{courseId}/semester/{semester}")
    public ResponseEntity<List<NotificationResponseDTO>> getByCourseAndSemester(
            @PathVariable Long courseId,
            @PathVariable Integer semester) {
        return ResponseEntity.ok(service.getTitlesByCourseAndSemester(courseId, semester));
    }



}
