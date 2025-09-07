package com.aristiec.schoolmanagementsystem.repository.library;

import com.aristiec.schoolmanagementsystem.dto.adminReport.MostBorrowedBookDTO;
import com.aristiec.schoolmanagementsystem.modal.library.BorrowRecord;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

       @Query("SELECT br FROM BorrowRecord br WHERE br.studentDetails.id = :studentId")
       List<BorrowRecord> findAllByStudentId(@Param("studentId") Long studentId);


    
    List<BorrowRecord> findByStudentDetailsIdAndReturnDateIsNull(Long studentId);


       @Query("SELECT br FROM BorrowRecord br WHERE br.bookCopy.book.bookId = :bookId")
       List<BorrowRecord> findAllByBookId(@Param("bookId") Long bookId);

       @Query("SELECT b FROM BorrowRecord b WHERE b.studentDetails.id = :studentId AND b.dueDate < :currentDate AND b.returnDate IS NULL")
       List<BorrowRecord> findOverdueBooksByStudent(@Param("studentId") Long studentId,
                     @Param("currentDate") LocalDate currentDate);

       @Query(value = "SELECT DATE_FORMAT(issue_date, '%Y-%m') AS month, COUNT(*) AS count FROM book_records GROUP BY month", nativeQuery = true)
       List<Object[]> countIssuedBooksPerMonth();

       @Query(value = "SELECT DATE_FORMAT(return_date, '%Y-%m') AS month, COUNT(*) AS count FROM book_records WHERE return_date IS NOT NULL GROUP BY month", nativeQuery = true)
       List<Object[]> countReturnedBooksPerMonth();

       @Query("SELECT COUNT(b), SUM(b.fine) " +
                     "FROM BorrowRecord b " +
                     "WHERE b.returnDate IS NULL AND b.dueDate < CURRENT_DATE")
       List<Object[]> findOverdueBooksAndTotalFine();

       @Query("SELECT new com.aristiec.schoolmanagementsystem.dto.adminReport.MostBorrowedBookDTO(b.bookCopy.book.title, COUNT(b)) "
                     +
                     "FROM BorrowRecord b " +
                     "GROUP BY b.bookCopy.book.id, b.bookCopy.book.title " +
                     "ORDER BY COUNT(b) DESC")
       List<MostBorrowedBookDTO> findMostBorrowedBooks();

       @Query("SELECT COUNT(DISTINCT b.studentDetails.id) FROM BorrowRecord b WHERE b.returnDate IS NULL")
       Long countDistinctActiveUsers();

     @Query("SELECT COUNT(b) FROM BorrowRecord b WHERE b.studentDetails.id = :studentId")
int countTotalBorrowedBooksByStudent(@Param("studentId") Long studentId);

       @Query("SELECT COUNT(br) FROM BorrowRecord br WHERE br.studentDetails.id = :studentId AND br.returnDate IS NULL")
       int countCurrentlyBorrowedByStudent(@Param("studentId") Long studentId);

       @Query("SELECT COUNT(br), COALESCE(SUM(br.fine), 0) FROM BorrowRecord br " +
                     "WHERE br.studentDetails.id = :studentId " +
                     "AND br.dueDate < :today " +
                     "AND (br.returnDate IS NULL OR br.returnDate > br.dueDate)")
       Object[] countOverdueBooksAndSumFine(@Param("studentId") Long studentId, @Param("today") LocalDate today);

       // List<BorrowRecord> findAllByStudentCode(String studentCode);

}
