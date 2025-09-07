package com.aristiec.schoolmanagementsystem.controller.admin.library;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.library.BorrowRecordRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.library.BorrowRecordResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.library.BorrowedStudentDTO;
import com.aristiec.schoolmanagementsystem.dto.library.DueBooksDTO;
import com.aristiec.schoolmanagementsystem.dto.library.IssuedBookDTO;
import com.aristiec.schoolmanagementsystem.dto.library.LibrarySummaryDTO;
import com.aristiec.schoolmanagementsystem.dto.library.StudentActivityDTO;
import com.aristiec.schoolmanagementsystem.service.library.BorrowRecordService;
import com.aristiec.schoolmanagementsystem.service.library.FinePaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/library/borrow")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@Tag(name = "Library Borrow Records", description = "Manage borrow records for library books")
public class BorrowRecordController {
    private final BorrowRecordService borrowRecordService;
    private final FinePaymentService finePaymentService;

    @PostMapping("/issue")
    @Operation(summary = "Issue Book to Student", description = "Issues the first available copy of the given book to a student")
    public ResponseEntity<BorrowRecordResponseDTO> issueBook(@RequestBody BorrowRecordRequestDTO dto) {
        return ResponseEntity.ok(borrowRecordService.issueBook(dto));
    }

    @PutMapping("/return/{borrowId}")
    @Operation(summary = "Return a Book", description = "Sets the return date to today and calculates fine (₹5/day if overdue)")
    public ResponseEntity<BorrowRecordResponseDTO> returnBook(@PathVariable Long borrowId) {
        BorrowRecordResponseDTO response = borrowRecordService.returnBook(borrowId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{borrowId}")
    @Operation(summary = "Get Borrow Record by ID", description = "Fetch borrow record with full book and student details")
    public ResponseEntity<BorrowRecordResponseDTO> getBorrowRecordById(@PathVariable Long borrowId) {
        return ResponseEntity.ok(borrowRecordService.getBorrowRecordById(borrowId));
    }

    @GetMapping("/student/{studentId}/issued")
    @Operation(summary = "Get currently issued books", description = "Returns only currently issued books (not returned)")
    public ResponseEntity<List<IssuedBookDTO>> getIssuedBooks(@PathVariable Long studentId) {
        return ResponseEntity.ok(borrowRecordService.getAllIssuedBooksByStudent(studentId));
    }

    @GetMapping("/book/{bookId}/borrowed-students")
    @Operation(summary = "Get students who borrowed the book", description = "Lists all students who borrowed any copy of the book")
    public ResponseEntity<List<BorrowedStudentDTO>> getStudentsWhoBorrowedBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(borrowRecordService.getAllStudentsWhoBorrowedBook(bookId));
    }

    @GetMapping("/student/{studentId}/overdue")
    @Operation(summary = "Get overdue books", description = "Returns all overdue books for a student")
    public ResponseEntity<List<IssuedBookDTO>> getOverdueBooksByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(borrowRecordService.getOverdueBooksByStudent(studentId));
    }

    @GetMapping("/books/totalBorrowed/{studentId}")
    public ResponseEntity<Integer> getTotalBooksBorrowed(@PathVariable Long studentId) {
        int total = borrowRecordService.getTotalBooksBorrowed(studentId);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/books/currentlyBorrowed/{studentId}")
    public ResponseEntity<Integer> getCurrentlyBorrowedBooks(@PathVariable Long studentId) {
        int total = borrowRecordService.getCurrentlyBorrowedBooks(studentId);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/books/overdue/{studentId}")
    public ResponseEntity<DueBooksDTO> getOverdueBooksandFine(@PathVariable Long studentId) {
        DueBooksDTO dto = borrowRecordService.getOverdueBooksAndFine(studentId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/student/{studentId}/library-summary")
    @Operation(summary = "Get student library summary", description = "Returns total books issued, due soon count with next due date, and total fine")
    public ResponseEntity<LibrarySummaryDTO> getLibrarySummary(@PathVariable Long studentId) {
        return ResponseEntity.ok(borrowRecordService.getLibrarySummary(studentId));
    }

    @GetMapping("/student/{studentId}/activity")
    public ResponseEntity<List<StudentActivityDTO>> getStudentActivity(@PathVariable Long studentId) {
        return ResponseEntity.ok(borrowRecordService.getStudentActivityHistory(studentId));
    }

}
