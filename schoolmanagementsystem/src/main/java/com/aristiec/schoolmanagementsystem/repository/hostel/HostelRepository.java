package com.aristiec.schoolmanagementsystem.repository.hostel;

import com.aristiec.schoolmanagementsystem.modal.hostel.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostelRepository extends JpaRepository<Hostel, Long> {
}