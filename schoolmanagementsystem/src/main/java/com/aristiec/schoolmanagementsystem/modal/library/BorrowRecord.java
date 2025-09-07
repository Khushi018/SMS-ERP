package com.aristiec.schoolmanagementsystem.modal.library;

import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "book_records")
@RequiredArgsConstructor
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Double fine;

    @OneToOne(mappedBy = "borrowRecord", cascade = CascadeType.ALL)
    private FinePayment finePayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_copy_id")
    @JsonIgnore
    private BookCopy bookCopy;
    

@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private StudentDetails studentDetails;
}
