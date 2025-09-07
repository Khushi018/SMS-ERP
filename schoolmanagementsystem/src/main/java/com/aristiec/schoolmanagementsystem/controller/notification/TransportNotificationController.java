package com.aristiec.schoolmanagementsystem.controller.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.notification.NotificationResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.notification.TransportNotificationRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.notification.TransportNotificationResponseDTO;
import com.aristiec.schoolmanagementsystem.service.notification.TransportNotificationService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/transport-notifications")

@SecurityRequirement(name = "bearerAuth")
public class TransportNotificationController {
    @Autowired
    private TransportNotificationService transportNotificationService;

    @PostMapping
    public ResponseEntity<TransportNotificationResponseDTO> createNotification(
            @RequestBody TransportNotificationRequestDTO dto) {
        return ResponseEntity.ok(transportNotificationService.createNotification(dto));
    }

    @GetMapping
    public ResponseEntity<List<TransportNotificationResponseDTO>> getAllNotifications() {
        return ResponseEntity.ok(transportNotificationService.getAllNotifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportNotificationResponseDTO> getNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(transportNotificationService.getNotificationById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        transportNotificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-route-vehicle")
    public ResponseEntity<List<NotificationResponseDTO>> getByRouteAndVehicle(
            @RequestParam Long routeId,
            @RequestParam Long vehicleId) {
        return ResponseEntity.ok(transportNotificationService.getByRouteAndVehicle(routeId, vehicleId));
    }
}
