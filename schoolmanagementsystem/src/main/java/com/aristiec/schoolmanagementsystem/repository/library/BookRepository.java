package com.aristiec.schoolmanagementsystem.repository.library;

import com.aristiec.schoolmanagementsystem.modal.library.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

     @Query("SELECT b FROM Book b WHERE " +
       "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
       "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
       "LOWER(b.isbn) LIKE LOWER(CONCAT('%', :keyword, '%'))")
List<Book> findByTitleOrAuthorOrIsbnContaining(@Param("keyword") String keyword);


    @Query("SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(CONCAT('%', :authorName, '%'))")
    List<Book> findByAuthor(String authorName);

    @Query("SELECT b FROM Book b WHERE " +
       "(:category IS NULL OR b.category = :category) AND " +
       "(:departmentId IS NULL OR b.department.id = :departmentId)")
List<Book> findBooksByCategoryAndDepartment(@Param("category") String category,
                                            @Param("departmentId") Long departmentId);

}
