package com.aristiec.schoolmanagementsystem.service.library;

import java.util.List;

import com.aristiec.schoolmanagementsystem.dto.BorrowRecordResponseDto;
import com.aristiec.schoolmanagementsystem.dto.library.BorrowRecordRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.library.BorrowRecordResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.library.BorrowedStudentDTO;
import com.aristiec.schoolmanagementsystem.dto.library.DueBooksDTO;
import com.aristiec.schoolmanagementsystem.dto.library.IssuedBookDTO;
import com.aristiec.schoolmanagementsystem.dto.library.LibrarySummaryDTO;
import com.aristiec.schoolmanagementsystem.dto.library.StudentActivityDTO;
import com.aristiec.schoolmanagementsystem.modal.library.BorrowRecord;

public interface BorrowRecordService {

    BorrowRecordResponseDTO issueBook(BorrowRecordRequestDTO dto);

     BorrowRecordResponseDTO returnBook(Long borrowId);

      BorrowRecordResponseDTO getBorrowRecordById(Long borrowId);

   List<IssuedBookDTO> getAllIssuedBooksByStudent(Long studentId);

  List<BorrowedStudentDTO> getAllStudentsWhoBorrowedBook(Long bookId);

   List<IssuedBookDTO> getOverdueBooksByStudent(Long studentId);

    LibrarySummaryDTO getLibrarySummary(Long studentId);
    
    int getTotalBooksBorrowed(Long studentId);
     int getCurrentlyBorrowedBooks(Long studentId);

      DueBooksDTO getOverdueBooksAndFine(Long studentId);

      List<StudentActivityDTO> getStudentActivityHistory(Long studentId);

}
