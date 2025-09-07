package com.aristiec.schoolmanagementsystem.modal.library;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "fine_payment")
@RequiredArgsConstructor
public class FinePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private BorrowRecord borrowRecord;

    private LocalDate paymentDate;
    private Double amount;
}
