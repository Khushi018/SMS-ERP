package com.aristiec.schoolmanagementsystem.service.hostel;

import com.aristiec.schoolmanagementsystem.dto.hostel.RoomAmenityDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.RoomDetailDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.RoomWithRoommatesDTO;
import com.aristiec.schoolmanagementsystem.modal.hostel.RoomDetail;

import java.util.List;

public interface RoomDetailService {
    RoomDetailDTO createRoomDetail(RoomDetailDTO roomDetailDTO);
    RoomDetailDTO getRoomDetailById(Long id);
    List<RoomDetailDTO> getAllRoomDetails();
    RoomWithRoommatesDTO getRoommatesByRoomNumber(String roomNumber);

}