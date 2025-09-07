package com.aristiec.schoolmanagementsystem.serviceImpl.library;


import com.aristiec.schoolmanagementsystem.repository.library.BorrowRecordRepository;
import com.aristiec.schoolmanagementsystem.repository.library.FinePaymentRepository;
import com.aristiec.schoolmanagementsystem.service.library.FinePaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinePaymentServiceImpl implements FinePaymentService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final FinePaymentRepository finePaymentRepository;

}
