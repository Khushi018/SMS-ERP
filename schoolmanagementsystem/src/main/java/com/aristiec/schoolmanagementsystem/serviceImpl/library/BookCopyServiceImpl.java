package com.aristiec.schoolmanagementsystem.serviceImpl.library;

import com.aristiec.schoolmanagementsystem.dto.library.BookCopyRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.library.BookCopyResponseDTO;
import com.aristiec.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.aristiec.schoolmanagementsystem.modal.library.Book;
import com.aristiec.schoolmanagementsystem.modal.library.BookCopy;
import com.aristiec.schoolmanagementsystem.repository.library.BookCopyRepository;
import com.aristiec.schoolmanagementsystem.repository.library.BookRepository;
import com.aristiec.schoolmanagementsystem.service.library.BookCopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCopyServiceImpl implements BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;

    @Override
    public BookCopyResponseDTO addBookCopy(BookCopyRequestDTO dto) {
         Book book = bookRepository.findById(dto.getBookId())
            .orElseThrow(() -> new RuntimeException("Book not found"));

        BookCopy copy = new BookCopy();
        copy.setBook(book);
        copy.setBookCondition(dto.getBookCondition());
        copy.setCount(dto.getCount());

        BookCopy saved = bookCopyRepository.save(copy);

        BookCopyResponseDTO response = new BookCopyResponseDTO();
        response.setCopyId(saved.getId());
        response.setBookCondition(saved.getBookCondition());
        response.setCount(saved.getCount());
        response.setBookId(book.getBookId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setIsbn(book.getIsbn());
        response.setCategory(book.getCategory());
        response.setShelfLocation(book.getShelfLocation());
        response.setEdition(book.getEdition());
        response.setDescription(book.getDescription());
        response.setImageUrl(book.getImageUrl());
        response.setDepartmentName(book.getDepartment().getName());

        return response;
    }

    @Override
    public BookCopyResponseDTO getBookCopyById(Long id) {
                 BookCopy copy = bookCopyRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("BookCopy not found"));

    Book book = copy.getBook();

    BookCopyResponseDTO response = new BookCopyResponseDTO();
    response.setCopyId(copy.getId());
    response.setBookCondition(copy.getBookCondition());
    response.setCount(copy.getCount());
    response.setBookId(book.getBookId());
    response.setTitle(book.getTitle());
    response.setAuthor(book.getAuthor());
    response.setIsbn(book.getIsbn());
    response.setCategory(book.getCategory());
    response.setShelfLocation(book.getShelfLocation());
    response.setEdition(book.getEdition());
    response.setDescription(book.getDescription());
    response.setImageUrl(book.getImageUrl());
    response.setDepartmentName(book.getDepartment().getName());

    return response;
    }

    @Override
    public BookCopyResponseDTO updateBookCopyById(Long id, BookCopyRequestDTO dto) {
        BookCopy copy = bookCopyRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("BookCopy not found"));

    Book book = bookRepository.findById(dto.getBookId())
        .orElseThrow(() -> new RuntimeException("Book not found"));

    copy.setBook(book);
    copy.setBookCondition(dto.getBookCondition());
    copy.setCount(dto.getCount());

    BookCopy updated = bookCopyRepository.save(copy);

    BookCopyResponseDTO response = new BookCopyResponseDTO();
    response.setCopyId(updated.getId());
    response.setBookCondition(updated.getBookCondition());
    response.setCount(updated.getCount());
    response.setBookId(book.getBookId());
    response.setTitle(book.getTitle());
    response.setAuthor(book.getAuthor());
    response.setIsbn(book.getIsbn());
    response.setCategory(book.getCategory());
    response.setShelfLocation(book.getShelfLocation());
    response.setEdition(book.getEdition());
    response.setDescription(book.getDescription());
    response.setImageUrl(book.getImageUrl());
    response.setDepartmentName(book.getDepartment().getName());

    return response;

    }

    @Override
    public void deleteBookCopyById(Long id) {
       BookCopy copy = bookCopyRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("BookCopy not found"));

    bookCopyRepository.delete(copy);
    }


}
