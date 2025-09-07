package com.aristiec.schoolmanagementsystem.controller.admin.library;

import com.aristiec.schoolmanagementsystem.dto.library.BookCopyRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.library.BookCopyResponseDTO;
import com.aristiec.schoolmanagementsystem.service.library.BookCopyService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/library/bookCopies")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Book Copy Management", description = "Manage physical copies of books in the library")
public class BookCopyController {
    private final BookCopyService bookCopyService;

    @PostMapping
    @Operation(summary = "Add Book Copy", description = "Adds a physical copy of a book with a given condition")
    public ResponseEntity<BookCopyResponseDTO> addBookCopy(@RequestBody BookCopyRequestDTO dto) {
        return ResponseEntity.ok(bookCopyService.addBookCopy(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book copy by ID", description = "Returns book copy and related book details")
    public ResponseEntity<BookCopyResponseDTO> getBookCopyById(@PathVariable Long id) {
        return ResponseEntity.ok(bookCopyService.getBookCopyById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book copy", description = "Update book condition or reassign to another book")
    public ResponseEntity<BookCopyResponseDTO> updateBookCopyById(
            @PathVariable Long id,
            @RequestBody BookCopyRequestDTO dto) {
        return ResponseEntity.ok(bookCopyService.updateBookCopyById(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book copy", description = "Deletes a book copy by ID")
    public ResponseEntity<String> deleteBookCopyById(@PathVariable Long id) {
        bookCopyService.deleteBookCopyById(id);
        return ResponseEntity.ok("Book copy deleted successfully");
    }

}
