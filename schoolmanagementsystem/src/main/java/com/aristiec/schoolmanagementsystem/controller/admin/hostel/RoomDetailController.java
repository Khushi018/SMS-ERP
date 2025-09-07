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

import com.aristiec.schoolmanagementsystem.dto.hostel.RoomDetailDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.RoomWithRoommatesDTO;
import com.aristiec.schoolmanagementsystem.modal.hostel.RoomDetail;
import com.aristiec.schoolmanagementsystem.service.hostel.RoomDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/room-detail")
@RequiredArgsConstructor

@SecurityRequirement(name = "bearerAuth") 
@Tag(name = "Room Details", description = "Manage hostel room details like type, capacity, and location")
public class RoomDetailController {

    private final RoomDetailService roomDetailService;

    @PostMapping
    @Operation(summary = "Create new room detail", description = "Adds a new room detail to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room detail created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid room detail input", content = @Content)
    })
    public ResponseEntity<RoomDetailDTO> createRoomDetail(
            @RequestBody @Parameter(description = "Room detail object") RoomDetailDTO roomDetail) {
        return new ResponseEntity<>(roomDetailService.createRoomDetail(roomDetail), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get room detail by ID", description = "Fetches room detail by room ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room detail found"),
            @ApiResponse(responseCode = "404", description = "Room detail not found", content = @Content)
    })
    public ResponseEntity<RoomDetailDTO> getRoomDetailById(
            @Parameter(description = "Room detail ID") @PathVariable Long id) {
        return ResponseEntity.ok(roomDetailService.getRoomDetailById(id));
    }

    @GetMapping
    @Operation(summary = "Get all room details", description = "Fetches all available room details")
    @ApiResponse(responseCode = "200", description = "List of room details returned")
    public ResponseEntity<List<RoomDetailDTO>> getAllRoomDetails() {
        return ResponseEntity.ok(roomDetailService.getAllRoomDetails());
    }

    @GetMapping("/room/{roomNumber}/roommates")
public ResponseEntity<RoomWithRoommatesDTO> getRoommates(@PathVariable String roomNumber) {
    return ResponseEntity.ok(roomDetailService.getRoommatesByRoomNumber(roomNumber));
}

}
