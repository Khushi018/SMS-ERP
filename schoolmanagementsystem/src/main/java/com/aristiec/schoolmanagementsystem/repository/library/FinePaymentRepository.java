package com.aristiec.schoolmanagementsystem.repository.library;

import com.aristiec.schoolmanagementsystem.modal.library.FinePayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinePaymentRepository extends JpaRepository<FinePayment, Long> {
}
