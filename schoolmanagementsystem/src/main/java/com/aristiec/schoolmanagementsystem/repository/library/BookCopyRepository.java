package com.aristiec.schoolmanagementsystem.repository.library;

import com.aristiec.schoolmanagementsystem.modal.library.BookCopy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    @Query("SELECT bc FROM BookCopy bc WHERE bc.book.bookId = :bookId AND " +
       "bc.id NOT IN (SELECT br.bookCopy.id FROM BorrowRecord br WHERE br.returnDate IS NULL)")
Optional<BookCopy> findFirstAvailableCopy(@Param("bookId") Long bookId);
   List<BookCopy> findByBookBookId(Long bookId);
}
