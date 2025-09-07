package com.aristiec.schoolmanagementsystem.controller.admin.hostel;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.hostel.RoomAllocationsDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.RoomAmenityDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.RoomDetailsByStudentDTO;
import com.aristiec.schoolmanagementsystem.modal.hostel.RoomAllocations;
import com.aristiec.schoolmanagementsystem.service.hostel.RoomAllocationsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/room-allocations")
@RequiredArgsConstructor

@SecurityRequirement(name = "bearerAuth") 
@Tag(name = "Room Allocations", description = "Manage hostel room allocations for students")
public class RoomAllocationsController {

    private final RoomAllocationsService roomAllocationsService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Allocate a room", description = "Assigns a hostel room to a student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room allocated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public ResponseEntity<String> create(
            @RequestBody @Parameter(description = "Room allocation data") RoomAllocationsDTO allocation) {
        return new ResponseEntity<>(roomAllocationsService.createRoomAllocation(allocation), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get room allocation by ID", description = "Fetches room allocation details using allocation ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room allocation found"),
            @ApiResponse(responseCode = "404", description = "Allocation not found", content = @Content)
    })
    public ResponseEntity<RoomAllocationsDTO> getById(
            @Parameter(description = "Room allocation ID") @PathVariable Long id) {

        return ResponseEntity.ok(roomAllocationsService.getRoomAllocationById(id));
    }

@GetMapping("/room-details/student/{studentId}")
public ResponseEntity<RoomDetailsByStudentDTO> getRoomDetailsByStudentId(@PathVariable Long studentId) {
    RoomDetailsByStudentDTO dto = roomAllocationsService.getRoomDetailsByStudentId(studentId);
    return ResponseEntity.ok(dto);
}


    @GetMapping
    @Operation(summary = "Get all room allocations", description = "Retrieves all room allocations")
    @ApiResponse(responseCode = "200", description = "List of room allocations retrieved")
    public ResponseEntity<List<RoomAllocationsDTO>> getAll() {
        return ResponseEntity.ok(roomAllocationsService.getAllAllocations());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a room allocation", description = "Removes a room allocation by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Room allocation deleted"),
            @ApiResponse(responseCode = "404", description = "Allocation not found", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the room allocation to delete") @PathVariable Long id) {
        roomAllocationsService.deleteRoomAllocation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/students/{studentId}/amenities")
public ResponseEntity<List<RoomAmenityDTO>> getRoomAmenities(@PathVariable Long studentId) {
    List<RoomAmenityDTO> amenities = roomAllocationsService.getAmenitiesByStudentId(studentId);
    return ResponseEntity.ok(amenities);
}

}
