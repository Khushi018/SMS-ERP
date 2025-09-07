package com.aristiec.schoolmanagementsystem.modal.library;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "librarian")
@RequiredArgsConstructor
public class Librarian {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long librarianId;

    private String name;
    private String email;
    private String phoneNumber;
    private String department;
}
