package com.aristiec.schoolmanagementsystem.service.details;


import com.aristiec.schoolmanagementsystem.dto.LibraryCardDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.LibraryCard;

public interface LibraryCardService {
    LibraryCard assignCardToStudent(LibraryCardDTO dto);
}
