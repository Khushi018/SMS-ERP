package com.aristiec.schoolmanagementsystem.repository.hostel;

import com.aristiec.schoolmanagementsystem.modal.hostel.RoomDetail;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDetailRepository extends JpaRepository<RoomDetail, Long> {
    Optional<RoomDetail> findByRoomNumber(String roomNumber);


}

