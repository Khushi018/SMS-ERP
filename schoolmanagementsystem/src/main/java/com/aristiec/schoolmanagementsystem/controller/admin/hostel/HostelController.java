package com.aristiec.schoolmanagementsystem.controller.admin.hostel;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.hostel.HostelDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.HostelInfoByStudentDTO;
import com.aristiec.schoolmanagementsystem.modal.hostel.Hostel;
import com.aristiec.schoolmanagementsystem.service.hostel.HostelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/hostel")
@RequiredArgsConstructor
@Tag(name = "Hostel Management", description = "Manage hostels and their details")

@SecurityRequirement(name = "bearerAuth") 
public class HostelController {

    private final HostelService hostelService;

    @PostMapping
    @Operation(summary = "Create a new hostel", description = "Adds a new hostel to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hostel created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<HostelDTO> createHostel(
            @RequestBody @Parameter(description = "Hostel details to be created") HostelDTO hostel) {
        return new ResponseEntity<>(hostelService.createHostel(hostel), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get hostel by ID", description = "Fetches hostel information by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hostel found"),
            @ApiResponse(responseCode = "404", description = "Hostel not found", content = @Content)
    })
    public ResponseEntity<HostelDTO> getHostelById(
            @Parameter(description = "ID of the hostel to fetch") @PathVariable Long id) {
        return ResponseEntity.ok(hostelService.getHostelById(id));
    }

    @GetMapping
    @Operation(summary = "Get all hostels", description = "Returns a list of all hostels")
    @ApiResponse(responseCode = "200", description = "List of hostels retrieved")
    public ResponseEntity<List<HostelDTO>> getAllHostels() {
        return ResponseEntity.ok(hostelService.getAllHostels());
    }

     @GetMapping("/student/{studentId}")
    public ResponseEntity<HostelInfoByStudentDTO> getHostelByStudentId(@PathVariable Long studentId) {
        HostelInfoByStudentDTO dto = hostelService.getHostelByStudentId(studentId);
        return ResponseEntity.ok(dto);
    }
}
