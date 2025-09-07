package com.aristiec.schoolmanagementsystem.service.library;

import com.aristiec.schoolmanagementsystem.dto.library.BookRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.library.BookResponseDTO;
import com.aristiec.schoolmanagementsystem.modal.library.Book;

import java.util.List;

public interface BookService {

   BookResponseDTO addBook(BookRequestDTO dto);

    BookResponseDTO getBookById(Long id);

   BookResponseDTO updateBookById(Long id, BookRequestDTO dto);

    void deleteBookById(Long id);

   List<BookResponseDTO> getAllBooks();

   List<BookResponseDTO> searchBooks(String keyword);

   List<BookResponseDTO> getBooksByCategoryAndDepartment(String category, Long departmentId);


}
