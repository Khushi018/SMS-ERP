package com.aristiec.schoolmanagementsystem.serviceImpl.library;

import com.aristiec.schoolmanagementsystem.dto.library.BookRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.library.BookResponseDTO;
import com.aristiec.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.aristiec.schoolmanagementsystem.modal.library.Book;
import com.aristiec.schoolmanagementsystem.modal.library.BookCopy;
import com.aristiec.schoolmanagementsystem.modal.staff.Department;
import com.aristiec.schoolmanagementsystem.repository.department.DepartmentRepository;
import com.aristiec.schoolmanagementsystem.repository.library.BookCopyRepository;
import com.aristiec.schoolmanagementsystem.repository.library.BookRepository;
import com.aristiec.schoolmanagementsystem.service.library.BookService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

     @Autowired
    private BookRepository bookRepository;
      @Autowired
     private DepartmentRepository departmentRepository;
      @Autowired
     private BookCopyRepository bookCopyRepository;
     @Autowired
    private ModelMapper modelMapper;

   

    @Override
    public BookResponseDTO getBookById(Long id) {
          Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));

    BookResponseDTO dto = modelMapper.map(book, BookResponseDTO.class);
    dto.setDepartmentName(book.getDepartment().getName());

       if (book.getCopies() != null && !book.getCopies().isEmpty()) {
        int totalCount = book.getCopies().stream()
                             .mapToInt(copy -> copy.getCount() != null ? copy.getCount() : 0)
                             .sum();
        dto.setCount(totalCount);
        dto.setAvailable(totalCount > 0);
    } else {
        dto.setCount(0);
        dto.setAvailable(false);
    }

    return dto;

    }

   

    @Override
    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(book);
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
          List<Book> books = bookRepository.findAll();
    List<BookResponseDTO> dtos = new ArrayList<>();

    for (Book book : books) {
        BookResponseDTO dto = modelMapper.map(book, BookResponseDTO.class);
        dto.setDepartmentName(book.getDepartment().getName());

         List<BookCopy> copies = bookCopyRepository.findByBookBookId(book.getBookId());
        int totalCount = copies.stream()
                               .mapToInt(copy -> copy.getCount() != null ? copy.getCount() : 0)
                               .sum();

        dto.setCount(totalCount);
        dto.setAvailable(totalCount > 0);

        dtos.add(dto);
    }

    return dtos;
    }


    @Override
    public List<BookResponseDTO> searchBooks(String keyword) {
        List<Book> books = bookRepository.findByTitleOrAuthorOrIsbnContaining(keyword);

    return books.stream().map(book -> {
        BookResponseDTO dto = modelMapper.map(book, BookResponseDTO.class);
        dto.setDepartmentName(book.getDepartment().getName());

      int totalCount = book.getCopies() != null
                ? book.getCopies().stream()
                    .mapToInt(copy -> copy.getCount() != null ? copy.getCount() : 0)
                    .sum()
                : 0;

        dto.setCount(totalCount);
        dto.setAvailable(totalCount > 0);

        return dto;
    }).toList();
    }



    @Override
    public BookResponseDTO addBook(BookRequestDTO dto) {

        Department department = departmentRepository.findById(dto.getDepartmentId())
        .orElseThrow(()->new RuntimeException("Department not found"));

        Book book=new Book();
        book.setTitle(dto.getTitle());
         book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setCategory(dto.getCategory());
        book.setShelfLocation(dto.getShelfLocation());
        book.setEdition(dto.getEdition());
        book.setDescription(dto.getDescription());
        book.setImageUrl(dto.getImageUrl());
        book.setDepartment(department);

        Book savedBook = bookRepository.save(book);

        BookResponseDTO responseDTO = modelMapper.map(savedBook, BookResponseDTO.class);
        responseDTO.setDepartmentName(department.getName());
        responseDTO.setCount(0);
        responseDTO.setAvailable(false);
        return responseDTO;

    }



    @Override
    public BookResponseDTO updateBookById(Long id, BookRequestDTO dto) {
             Book existingBook = bookRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Book not found"));

    Department department = departmentRepository.findById(dto.getDepartmentId())
        .orElseThrow(() -> new RuntimeException("Department not found"));

    existingBook.setTitle(dto.getTitle());
    existingBook.setAuthor(dto.getAuthor());
    existingBook.setIsbn(dto.getIsbn());
    existingBook.setCategory(dto.getCategory());
    existingBook.setShelfLocation(dto.getShelfLocation());
    existingBook.setEdition(dto.getEdition());
    existingBook.setDescription(dto.getDescription());
    existingBook.setImageUrl(dto.getImageUrl());
    existingBook.setDepartment(department);

    Book updated = bookRepository.save(existingBook);

    BookResponseDTO response = modelMapper.map(updated, BookResponseDTO.class);
    response.setDepartmentName(department.getName());

     int totalCount = updated.getCopies() != null
            ? updated.getCopies().stream()
                .mapToInt(copy -> copy.getCount() != null ? copy.getCount() : 0)
                .sum()
            : 0;

    response.setCount(totalCount);
    response.setAvailable(totalCount > 0);

    return response;

    }



    @Override
    public List<BookResponseDTO> getBooksByCategoryAndDepartment(String category, Long departmentId) {
           List<Book> books = bookRepository.findBooksByCategoryAndDepartment(category, departmentId);

    return books.stream().map(book -> {
        BookResponseDTO dto = modelMapper.map(book, BookResponseDTO.class);
        dto.setDepartmentName(book.getDepartment().getName());
          
         int totalCount = book.getCopies() != null
                ? book.getCopies().stream()
                    .mapToInt(copy -> copy.getCount() != null ? copy.getCount() : 0)
                    .sum()
                : 0;

        dto.setCount(totalCount);
        dto.setAvailable(totalCount > 0);


        return dto;
    }).collect(Collectors.toList());
    
    }
}
