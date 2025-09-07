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

import com.aristiec.schoolmanagementsystem.transport.dto.RouteVehicleAssignmentDto;
import com.aristiec.schoolmanagementsystem.transport.service.RouteVehicleAssignmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/transport/assign-vehicle")
public class RouteVehicleAssignmentController {

    @Autowired
    private RouteVehicleAssignmentService service;

    @PostMapping("/assign")
    public ResponseEntity<RouteVehicleAssignmentDto> assign(@RequestBody RouteVehicleAssignmentDto dto) {
        return ResponseEntity.status(201).body(service.assignVehicle(dto));
    }

    @GetMapping("/route/{routeId}")
    public ResponseEntity<List<RouteVehicleAssignmentDto>> getByRoute(@PathVariable Long routeId) {
        return ResponseEntity.ok(service.getAssignmentsByRoute(routeId));
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<RouteVehicleAssignmentDto>> getByVehicle(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(service.getAssignmentsByVehicle(vehicleId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}