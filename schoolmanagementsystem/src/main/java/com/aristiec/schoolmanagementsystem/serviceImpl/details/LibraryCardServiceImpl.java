package com.aristiec.schoolmanagementsystem.serviceImpl.details;

import com.aristiec.schoolmanagementsystem.dto.LibraryCardDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.LibraryCard;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.repository.details.LibraryCardRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.service.details.LibraryCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryCardServiceImpl implements LibraryCardService {

    @Autowired
    private StudentDetailsRepository studentRepository;

    @Autowired
    private LibraryCardRepository libraryCardRepository;

    public LibraryCard assignCardToStudent(LibraryCardDTO dto) {
        StudentDetails student = studentRepository.findById(dto.getStudentId())
            .orElseThrow(() -> new RuntimeException("Student not found"));

        LibraryCard card = new LibraryCard();
        card.setCardNumber(dto.getCardNumber());
        card.setStudent(student);

        student.setLibraryCard(card);

        return libraryCardRepository.save(card);
    }
}
