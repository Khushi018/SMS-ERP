package com.aristiec.schoolmanagementsystem.serviceImpl.library;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.constant.enums.BookConditionEnum;
import com.aristiec.schoolmanagementsystem.dto.BorrowRecordResponseDto;
import com.aristiec.schoolmanagementsystem.dto.library.BorrowRecordRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.library.BorrowRecordResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.library.BorrowedStudentDTO;
import com.aristiec.schoolmanagementsystem.dto.library.DueBooksDTO;
import com.aristiec.schoolmanagementsystem.dto.library.IssuedBookDTO;
import com.aristiec.schoolmanagementsystem.dto.library.LibrarySummaryDTO;
import com.aristiec.schoolmanagementsystem.dto.library.StudentActivityDTO;
import com.aristiec.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.library.Book;
import com.aristiec.schoolmanagementsystem.modal.library.BookCopy;
import com.aristiec.schoolmanagementsystem.modal.library.BorrowRecord;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.library.BookCopyRepository;
import com.aristiec.schoolmanagementsystem.repository.library.BookRepository;
import com.aristiec.schoolmanagementsystem.repository.library.BorrowRecordRepository;
import com.aristiec.schoolmanagementsystem.service.library.BorrowRecordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BorrowRecordServiceImpl implements BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final BookCopyRepository bookCopyRepository;
    private final StudentDetailsRepository studentRepository;
    private final BookRepository bookRepository;
   

    @Override
    public BorrowRecordResponseDTO returnBook(Long borrowId) {
       BorrowRecord record = borrowRecordRepository.findById(borrowId)
        .orElseThrow(() -> new RuntimeException("Borrow record not found"));

    if (record.getReturnDate() != null) {
        throw new RuntimeException("Book already returned.");
    }

    LocalDate today = LocalDate.now();
    record.setReturnDate(today);

    long daysLate = java.time.temporal.ChronoUnit.DAYS.between(record.getDueDate(), today);
    double fine = daysLate > 0 ? daysLate * 5.0 : 0.0;
    record.setFine(fine);

    // Increment the count of book copy
    BookCopy copy = record.getBookCopy();
    copy.setCount(copy.getCount() + 1);
    bookCopyRepository.save(copy);

    BorrowRecord updated = borrowRecordRepository.save(record);

    StudentDetails student = updated.getStudentDetails();
    Book book = copy.getBook();

    BorrowRecordResponseDTO response = new BorrowRecordResponseDTO();
    response.setBorrowId(updated.getId());
    response.setIssueDate(updated.getIssueDate());
    response.setDueDate(updated.getDueDate());
    response.setReturnDate(updated.getReturnDate());
    response.setFine(updated.getFine());

    response.setStudentId(student.getId());
    response.setStudentName(student.getFirstName() + " " + student.getLastName());
    response.setBookCopyId(copy.getId());
    response.setBookTitle(book.getTitle());
    response.setAuthor(book.getAuthor());
    response.setIsbn(book.getIsbn());

    return response;
    }


 

    @Override
    public List<IssuedBookDTO> getAllIssuedBooksByStudent(Long studentId) {
            List<BorrowRecord> records = borrowRecordRepository.findByStudentDetailsIdAndReturnDateIsNull(studentId);

    return records.stream().map(record -> {
        Book book = record.getBookCopy().getBook();

        IssuedBookDTO dto = new IssuedBookDTO();
        dto.setBookTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIssueDate(record.getIssueDate());
        dto.setDueDate(record.getDueDate());
        dto.setImageUrl(book.getImageUrl());

        return dto;
    }).collect(Collectors.toList());
    }

    @Override
    public List<BorrowedStudentDTO> getAllStudentsWhoBorrowedBook(Long bookId) {
         List<BorrowRecord> records = borrowRecordRepository.findAllByBookId(bookId);

    return records.stream().map(record -> {
        StudentDetails student = record.getStudentDetails();

        BorrowedStudentDTO dto = new BorrowedStudentDTO();
        dto.setStudentId(student.getId());
        dto.setStudentName(student.getFirstName() + " " + student.getLastName());
        dto.setIssueDate(record.getIssueDate());
        dto.setReturnDate(record.getReturnDate());
        dto.setFine(record.getFine());

        return dto;
    }).collect(Collectors.toList());

    }

    @Override
    public List<IssuedBookDTO> getOverdueBooksByStudent(Long studentId) {
         LocalDate today = LocalDate.now();
    List<BorrowRecord> records = borrowRecordRepository.findOverdueBooksByStudent(studentId, today);

    return records.stream().map(record -> {
        Book book = record.getBookCopy().getBook();

        IssuedBookDTO dto = new IssuedBookDTO();
        dto.setBookTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIssueDate(record.getIssueDate());
        dto.setDueDate(record.getDueDate());
        dto.setImageUrl(book.getImageUrl());

        return dto;
    }).collect(Collectors.toList());
    }

    @Override
    public int getTotalBooksBorrowed(Long studentId) {
           return borrowRecordRepository.countTotalBorrowedBooksByStudent(studentId);


    }

    @Override
    public int getCurrentlyBorrowedBooks(Long studentId) {
        return borrowRecordRepository.countCurrentlyBorrowedByStudent(studentId);

    }

    @Override
    public DueBooksDTO getOverdueBooksAndFine(Long studentId) {
      LocalDate today = LocalDate.now();
    List<BorrowRecord> overdueBooks = borrowRecordRepository.findOverdueBooksByStudent(studentId, today);

    int count = overdueBooks.size();
    double totalFine = overdueBooks.stream()
        .mapToDouble(record -> {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(record.getDueDate(), today);
            return daysLate * 5.0; // ₹5 fine per day
        })
        .sum();

    DueBooksDTO dto = new DueBooksDTO();
    dto.setOverdueCount(count);
    dto.setTotalFine(totalFine);

    return dto;

    }

    @Override
    public BorrowRecordResponseDTO issueBook(BorrowRecordRequestDTO dto) {
         StudentDetails student = studentRepository.findById(dto.getStudentId())
            .orElseThrow(() -> new RuntimeException("Student not found"));

        Book book = bookRepository.findById(dto.getBookId())
        .orElseThrow(() -> new RuntimeException("Book not found"));

    List<BookCopy> copies = bookCopyRepository.findByBookBookId(book.getBookId());

    BookCopy availableCopy = copies.stream()
        .filter(copy -> copy.getCount() > 0)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("No available copy for this book"));

    // Decrease count
    availableCopy.setCount(availableCopy.getCount() - 1);
    bookCopyRepository.save(availableCopy);

        BorrowRecord record = new BorrowRecord();
        record.setBookCopy(availableCopy);
        record.setStudentDetails(student);
        record.setIssueDate(dto.getIssueDate());
        record.setDueDate(dto.getDueDate());
        record.setFine(0.0); // initially no fine

        BorrowRecord saved = borrowRecordRepository.save(record);

        // Prepare response
        BorrowRecordResponseDTO response = new BorrowRecordResponseDTO();
        response.setBorrowId(saved.getId());
        response.setIssueDate(saved.getIssueDate());
        response.setDueDate(saved.getDueDate());
        response.setReturnDate(saved.getReturnDate());
        response.setFine(saved.getFine());

        response.setStudentId(student.getId());
        response.setStudentName(student.getFirstName()+" "+student.getLastName());

        response.setBookCopyId(availableCopy.getId());
        response.setBookTitle(availableCopy.getBook().getTitle());
        response.setAuthor(availableCopy.getBook().getAuthor());
        response.setIsbn(availableCopy.getBook().getIsbn());

        return response;
    }




    @Override
    public BorrowRecordResponseDTO getBorrowRecordById(Long borrowId) {
           BorrowRecord record = borrowRecordRepository.findById(borrowId)
        .orElseThrow(() -> new RuntimeException("Borrow record not found"));

    StudentDetails student = record.getStudentDetails();
    BookCopy copy = record.getBookCopy();
    Book book = copy.getBook();

    BorrowRecordResponseDTO response = new BorrowRecordResponseDTO();
    response.setBorrowId(record.getId());
    response.setIssueDate(record.getIssueDate());
    response.setDueDate(record.getDueDate());
    response.setReturnDate(record.getReturnDate());
    response.setFine(record.getFine());

    response.setStudentId(student.getId());
    response.setStudentName(student.getFirstName() + " " + student.getLastName());

    response.setBookCopyId(copy.getId());
    response.setBookTitle(book.getTitle());
    response.setAuthor(book.getAuthor());
    response.setIsbn(book.getIsbn());

    return response;

    }




    @Override
    public LibrarySummaryDTO getLibrarySummary(Long studentId) {
      LocalDate today = LocalDate.now();
    LocalDate threeDaysLater = today.plusDays(3);

    // 1. Total books issued
    int totalIssued = borrowRecordRepository.countCurrentlyBorrowedByStudent(studentId);

    // 2. Active borrowed books (to compute dueSoon and nextDueDate)
    List<BorrowRecord> activeBorrows = borrowRecordRepository.findByStudentDetailsIdAndReturnDateIsNull(studentId);

    int dueSoonCount = (int) activeBorrows.stream()
            .filter(b -> {
                LocalDate due = b.getDueDate();
                return due != null && !due.isBefore(today) && !due.isAfter(threeDaysLater);
            })
            .count();

    LocalDate nextDueDate = activeBorrows.stream()
            .map(BorrowRecord::getDueDate)
            .filter(Objects::nonNull)
            .min(LocalDate::compareTo)
            .orElse(null);

    // 3. Overdue fine calculation
    Object[] overdueData = borrowRecordRepository.countOverdueBooksAndSumFine(studentId, today);
    double totalFine = overdueData != null && overdueData.length > 1
            ? ((Number) overdueData[1]).doubleValue()
            : 0.0;

    // 4. Build DTO
    LibrarySummaryDTO summary = new LibrarySummaryDTO();
    summary.setTotalBooksIssued(totalIssued);
    summary.setDueSoonCount(dueSoonCount);
    summary.setNextDueDate(nextDueDate);
    summary.setTotalFine(totalFine);

    return summary;

    }




    @Override
    public List<StudentActivityDTO> getStudentActivityHistory(Long studentId) {
      List<BorrowRecord> records = borrowRecordRepository.findAllByStudentId(studentId);

    return records.stream().map(record -> {
        Book book = record.getBookCopy().getBook();

        String status;
        if (record.getReturnDate() == null) {
            status = "Not Returned";
        } else if (record.getReturnDate().isAfter(record.getDueDate())) {
            status = "Returned Late";
        } else {
            status = "Returned";
        }

        return new StudentActivityDTO(
            book.getTitle(),
            book.getIsbn(),
            record.getIssueDate(),
            record.getDueDate(),
            record.getReturnDate(),
            status,
            record.getFine()
        );
    }).collect(Collectors.toList());
    }


   
}
