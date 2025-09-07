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

import com.aristiec.schoolmanagementsystem.transport.dto.RouteTimeTableDto;
import com.aristiec.schoolmanagementsystem.transport.service.RouteTimeTableService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/transport/timetables")
public class RouteTimeTableController {

    @Autowired
    private RouteTimeTableService service;

    @PostMapping("/create")
    public ResponseEntity<RouteTimeTableDto> create(@RequestBody RouteTimeTableDto dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    @GetMapping("/route/{routeId}")
    public ResponseEntity<List<RouteTimeTableDto>> listByRoute(@PathVariable Long routeId) {
        return ResponseEntity.ok(service.getByRoute(routeId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}