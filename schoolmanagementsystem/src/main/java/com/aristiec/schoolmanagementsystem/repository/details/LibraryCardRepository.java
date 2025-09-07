package com.aristiec.schoolmanagementsystem.repository.details;

import com.aristiec.schoolmanagementsystem.modal.admission.LibraryCard;
import com.aristiec.schoolmanagementsystem.modal.admission.ParentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface LibraryCardRepository extends JpaRepository<LibraryCard, Long> {
}
