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

import com.aristiec.schoolmanagementsystem.transport.dto.DriverDto;
import com.aristiec.schoolmanagementsystem.transport.service.DriverService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/transport/drivers")
public class DriverController {

    @Autowired
    private DriverService service;

    @Operation(summary = "Create a new driver", description = "Creates a new driver with the provided details.")
    @PostMapping("/add")
    public ResponseEntity<DriverDto> create(@RequestBody DriverDto dto) {
        return ResponseEntity.status(201).body(service.createDriver(dto));
    }

    @Operation(summary = "Get all drivers", description = "Returns a list of all drivers.")
    @GetMapping("/all")
    public ResponseEntity<List<DriverDto>> list() {
        return ResponseEntity.ok(service.getAllDrivers());
    }

    @Operation(summary = "Get a driver by ID", description = "Returns a driver by its unique identifier.")
    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDriverById(id));
    }

    @Operation(summary = "Delete a driver by ID", description = "Deletes a driver by its unique identifier.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get drivers by vehicle ID", description = "Returns drivers assigned to a specific vehicle.")
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<DriverDto>> getDriversByVehicle(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(service.getDriversByVehicleId(vehicleId));
    }
}