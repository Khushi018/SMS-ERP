package com.aristiec.schoolmanagementsystem.controller.admin.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.LibraryCardDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.LibraryCard;
import com.aristiec.schoolmanagementsystem.service.details.LibraryCardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/library-card")
@Tag(name = "Library Cards", description = "Assign and manage student library cards")

@SecurityRequirement(name = "bearerAuth") 
public class LibraryCardController {

    @Autowired
    private LibraryCardService libraryCardService;

    @PostMapping("/assign")
    @Operation(summary = "Assign library card", description = "Assigns a new library card to a student using student ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Library card assigned successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided", content = @Content)
    })
    public LibraryCard assignCard(
            @RequestBody @Parameter(description = "Student ID and card issue details") LibraryCardDTO dto) {
        return libraryCardService.assignCardToStudent(dto);
    }
}
