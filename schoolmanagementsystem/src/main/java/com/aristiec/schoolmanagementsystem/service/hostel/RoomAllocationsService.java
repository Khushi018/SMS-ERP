package com.aristiec.schoolmanagementsystem.service.hostel;

import com.aristiec.schoolmanagementsystem.dto.hostel.RoomAllocationsDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.RoomAmenityDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.RoomDetailsByStudentDTO;
import com.aristiec.schoolmanagementsystem.modal.hostel.RoomAllocations;

import java.util.List;

public interface RoomAllocationsService {

  String createRoomAllocation(RoomAllocationsDTO roomAllocationsDTO);
    RoomAllocationsDTO getRoomAllocationById(Long id);
    List<RoomAllocationsDTO> getAllAllocations();
    void deleteRoomAllocation(Long id);
    RoomDetailsByStudentDTO getRoomDetailsByStudentId(Long studentId);
         List<RoomAmenityDTO> getAmenitiesByStudentId(Long studentId);

}

