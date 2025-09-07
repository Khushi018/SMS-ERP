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

import com.aristiec.schoolmanagementsystem.transport.dto.VehicleDto;
import com.aristiec.schoolmanagementsystem.transport.service.VehicleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/transport/vehicles")          

@Tag(name = "Vehicle Management", description = "Operations related to managing transport vehicles.")
public class VehicleController {

    @Autowired
    private VehicleService service;

    @Operation(summary = "Create a new vehicle", description = "Creates a new vehicle with the provided details.")
    @PostMapping
    public ResponseEntity<VehicleDto> create(@RequestBody VehicleDto dto) {
        VehicleDto created = service.createVehicle(dto);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "Get all vehicles", description = "Returns a list of all vehicles.")
    @GetMapping
    public ResponseEntity<List<VehicleDto>> list() {
        return ResponseEntity.ok(service.getAllVehicles());
    }

    @Operation(summary = "Get a vehicle by ID", description = "Returns a vehicle by its unique identifier.")
    @GetMapping("/{id}")
    public ResponseEntity<VehicleDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getVehicleById(id));
    }

    @Operation(summary = "Delete a vehicle by ID", description = "Deletes a vehicle by its unique identifier.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all vehicle by route ID", description = "Returns the number of vehicles assigned to a specific route.")
    @GetMapping("/route/{routeId}")
    public ResponseEntity<List<VehicleDto>> getByRoute(@PathVariable Long routeId) {
        return ResponseEntity.ok(service.getVehiclesByRouteId(routeId));
    }

}