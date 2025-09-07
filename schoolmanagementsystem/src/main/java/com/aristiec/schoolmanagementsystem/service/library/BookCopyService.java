package com.aristiec.schoolmanagementsystem.service.library;

import com.aristiec.schoolmanagementsystem.constant.enums.BookConditionEnum;
import com.aristiec.schoolmanagementsystem.dto.library.BookCopyRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.library.BookCopyResponseDTO;
import com.aristiec.schoolmanagementsystem.modal.library.BookCopy;

public interface BookCopyService {
   
     BookCopyResponseDTO addBookCopy(BookCopyRequestDTO dto);

  BookCopyResponseDTO getBookCopyById(Long id);

BookCopyResponseDTO updateBookCopyById(Long id, BookCopyRequestDTO dto);

    void deleteBookCopyById(Long id);
}
