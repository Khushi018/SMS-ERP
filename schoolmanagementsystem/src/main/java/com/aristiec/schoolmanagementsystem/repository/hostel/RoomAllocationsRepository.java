package com.aristiec.schoolmanagementsystem.repository.hostel;

import com.aristiec.schoolmanagementsystem.modal.hostel.RoomAllocations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomAllocationsRepository extends JpaRepository<RoomAllocations, Long> {
    Optional<RoomAllocations> findByStudentDetailsId(Long studentId);
    int countByRoomDetailRoomDetailIdAndIsVacatedFalse(Long roomId);
    Optional<RoomAllocations> findByStudentDetailsIdAndIsVacatedFalse(Long studentId);



}
