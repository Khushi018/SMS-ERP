package com.aristiec.schoolmanagementsystem.serviceImpl.library;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.dto.library.BookResponseDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.library.Book;
import com.aristiec.schoolmanagementsystem.modal.library.Wishlist;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.library.BookRepository;
import com.aristiec.schoolmanagementsystem.repository.library.WishlistRepository;
import com.aristiec.schoolmanagementsystem.service.library.WishlistService;

@Service
public class WishlistServiceImpl implements WishlistService {

      @Autowired
     private WishlistRepository wishlistRepository;

      @Autowired
     private BookRepository bookRepository;

      @Autowired
     private StudentDetailsRepository studentDetailsRepository;

       @Autowired
    private ModelMapper modelMapper;



    @Override
    public void addToWishlist(Long studentId, Long bookId) {
         if (wishlistRepository.existsByStudentIdAndBookBookId(studentId, bookId)) {
        throw new RuntimeException("Book already in wishlist");
    }

    StudentDetails student = studentDetailsRepository.findById(studentId)
        .orElseThrow(() -> new RuntimeException("Student not found"));
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new RuntimeException("Book not found"));

    Wishlist wishlist = new Wishlist(null, student, book, LocalDate.now());
       wishlistRepository.save(wishlist);
    }

    @Override
    public void removeFromWishlist(Long studentId, Long bookId) {
       wishlistRepository.deleteByStudentIdAndBookBookId(studentId, bookId);
    }

    @Override
    public List<BookResponseDTO> getWishlistBooks(Long studentId) {
       List<Wishlist> wishlistItems = wishlistRepository.findByStudentId(studentId);

    return wishlistItems.stream().map(wishlist -> {
        Book book = wishlist.getBook();

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
