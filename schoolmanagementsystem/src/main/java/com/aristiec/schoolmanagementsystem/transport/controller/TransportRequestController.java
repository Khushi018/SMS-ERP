package com.aristiec.schoolmanagementsystem.transport.controller;

import com.aristiec.schoolmanagementsystem.constant.enums.RequestStatusEnum;
import com.aristiec.schoolmanagementsystem.transport.dto.TransportRequestCreateDto;
import com.aristiec.schoolmanagementsystem.transport.dto.TransportRequestReadDto;
import com.aristiec.schoolmanagementsystem.transport.modal.TransportRequest;
import com.aristiec.schoolmanagementsystem.transport.service.TransportRequestService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transport-requests")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
/**
 * Controller for managing transport requests in the School Management System.
 * Provides endpoints for creating, retrieving, updating, and deleting transport requests.
 *
 * <p>
 * Endpoints:
 * <ul>
 *   <li>Create a new transport request</li>
 *   <li>Retrieve all transport requests</li>
 *   <li>Retrieve a transport request by ID</li>
 *   <li>Update a transport request</li>
 *   <li>Delete a transport request</li>
 *   <li>Update the status of a transport request</li>
 * </ul>
 * </p>
 */
@Tag(name = "Transport Request Management", description = "Manage transport requests and their related details")
public class TransportRequestController {
    @Autowired
    private TransportRequestService transportRequestService;

    @PostMapping
    public ResponseEntity<TransportRequestReadDto> createTransportRequest(@RequestBody TransportRequestCreateDto createDto) {
        TransportRequestReadDto transportRequest = transportRequestService.createTransportRequest(createDto);
        return new ResponseEntity<>(transportRequest, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransportRequestReadDto>> getAllTransportRequests() {
        List<TransportRequestReadDto> transportRequests = transportRequestService.getAllTransportRequests();
        return new ResponseEntity<>(transportRequests, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportRequestReadDto> getTransportRequestById(@PathVariable Long id) {
        Optional<TransportRequestReadDto> transportRequest = transportRequestService.getTransportRequestById(id);
        return transportRequest.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransportRequestReadDto> updateTransportRequest(
            @PathVariable Long id,
            @RequestBody TransportRequestCreateDto updateDto) {
        TransportRequestReadDto updatedTransportRequest = transportRequestService.updateTransportRequest(id, updateDto);
        return updatedTransportRequest != null ?
                new ResponseEntity<>(updatedTransportRequest, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransportRequest(@PathVariable Long id) {
        boolean isDeleted = transportRequestService.deleteTransportRequest(id);
        return isDeleted ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<TransportRequestReadDto> updateStatus(
            @PathVariable Long id,
            @PathVariable RequestStatusEnum status
    ) {
        TransportRequestReadDto updatedRequest = transportRequestService.updateRequestStatus(id, status);
        return ResponseEntity.ok(updatedRequest);
    }
}
