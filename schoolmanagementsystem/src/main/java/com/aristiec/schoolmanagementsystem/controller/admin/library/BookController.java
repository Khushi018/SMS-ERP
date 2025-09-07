package com.aristiec.schoolmanagementsystem.controller.admin.library;

import com.aristiec.schoolmanagementsystem.dto.library.BookRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.library.BookResponseDTO;
import com.aristiec.schoolmanagementsystem.modal.library.Book;
import com.aristiec.schoolmanagementsystem.modal.library.BookCopy;
import com.aristiec.schoolmanagementsystem.service.library.BookService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/library")
@RequiredArgsConstructor

@SecurityRequirement(name = "bearerAuth") 
public class BookController {

    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<BookResponseDTO> addBook(@RequestBody BookRequestDTO dto) {
        BookResponseDTO response = bookService.addBook(dto);
        return ResponseEntity.ok(response);
    }


   @GetMapping("/book/{id}")
public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
    return ResponseEntity.ok(bookService.getBookById(id));
}


 @GetMapping("/books/search")
@Operation(summary = "Search books by title, author, or ISBN",
           description = "Returns a list of books where the title, author, or ISBN contains the given keyword (case-insensitive).")
public ResponseEntity<List<BookResponseDTO>>searchBooks(
        @RequestParam String keyword) {
    return ResponseEntity.ok(bookService.searchBooks(keyword));
}

   

    @GetMapping("/books/all")
   public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
    return ResponseEntity.ok(bookService.getAllBooks());
     }


   @PutMapping("/book/update/{id}")
public ResponseEntity<BookResponseDTO> updateBookById(
        @PathVariable Long id,
        @RequestBody BookRequestDTO dto) {
    return ResponseEntity.ok(bookService.updateBookById(id, dto));
    }

    @DeleteMapping("/book/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok("Success");
    }


    @GetMapping("/filter-by-category-department")
@Operation(summary = "Get books by category and department", description = "Filter books by category and/or department ID")
public ResponseEntity<List<BookResponseDTO>> getBooksByCategoryAndDepartment(
        @RequestParam(required = false) String category,
        @RequestParam(required = false) Long departmentId) {
    return ResponseEntity.ok(bookService.getBooksByCategoryAndDepartment(category, departmentId));
}

}
