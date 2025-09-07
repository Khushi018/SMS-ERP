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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.transport.dto.StoppageDto;
import com.aristiec.schoolmanagementsystem.transport.service.StoppageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/transport/stoppages")
@Tag(name = "Stoppage Management", description = "Operations related to managing transport stoppages.")
public class StoppageController {

    @Autowired
    private StoppageService service;

    @PostMapping
    public ResponseEntity<List<StoppageDto>> create(@RequestBody List<StoppageDto> dtos) {
        List<StoppageDto> created = dtos.stream()
                .map(service::create)
                .toList();
        return ResponseEntity.status(201).body(created);
    }

    @PostMapping("/group")
    public ResponseEntity<List<StoppageDto>> createGroup(
            @RequestParam String groupName,
            @RequestBody List<StoppageDto> dtos) {
        return ResponseEntity.status(201).body(service.createGroupedStoppages(groupName, dtos));
    }

    @GetMapping("/route/{routeId}")
    public ResponseEntity<List<StoppageDto>> listByRoute(@PathVariable Long routeId) {
        return ResponseEntity.ok(service.getByRoute(routeId));
    }

    @GetMapping
    public ResponseEntity<List<StoppageDto>> getAll() {
        return ResponseEntity.ok(service.getAllStoppages());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }

}