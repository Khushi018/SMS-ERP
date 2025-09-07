package com.aristiec.schoolmanagementsystem.transport.repository;

import com.aristiec.schoolmanagementsystem.transport.modal.TransportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportRequestRepository extends JpaRepository<TransportRequest, Long> {
}
