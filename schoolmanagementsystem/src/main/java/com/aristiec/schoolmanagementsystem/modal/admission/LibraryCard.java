package com.aristiec.schoolmanagementsystem.modal.admission;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "library_card")
@Data
public class LibraryCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;

    @OneToOne
    private StudentDetails student;

}
