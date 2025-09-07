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

import com.aristiec.schoolmanagementsystem.transport.dto.RouteDTO;
import com.aristiec.schoolmanagementsystem.transport.service.RouteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/transport/routes")
@Tag(name = "Route Management", description = "Operations related to managing transport routes.")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Operation(summary = "Create a new route", description = "Creates a new route with the provided details.")
    @PostMapping("/add")
    public ResponseEntity<RouteDTO> create(@RequestBody RouteDTO dto) {
        return ResponseEntity.status(201).body(routeService.createRoute(dto));
    }

    @Operation(summary = "Get all routes", description = "Returns a list of all routes.")
    @GetMapping
    public ResponseEntity<List<RouteDTO>> list() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    @Operation(summary = "Get a route by ID", description = "Returns a route by its unique identifier.")
    @GetMapping("/{id}")
    public ResponseEntity<RouteDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.getRouteById(id));
    }

    @Operation(summary = "Delete a route by ID", description = "Deletes a route by its unique identifier.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
}