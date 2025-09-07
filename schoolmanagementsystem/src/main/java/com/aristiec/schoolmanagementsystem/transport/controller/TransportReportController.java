package com.aristiec.schoolmanagementsystem.transport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.transport.dto.TransportReportDto;
import com.aristiec.schoolmanagementsystem.transport.service.TransportReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/transport/report")

public class TransportReportController {

    @Autowired
    private TransportReportService service;

    @GetMapping
    public ResponseEntity<List<TransportReportDto>> getReport() {
        return ResponseEntity.ok(service.getReport());
    }
}