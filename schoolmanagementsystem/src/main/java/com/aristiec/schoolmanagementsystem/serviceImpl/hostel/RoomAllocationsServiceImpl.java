package com.aristiec.schoolmanagementsystem.serviceImpl.hostel;

import com.aristiec.schoolmanagementsystem.dto.hostel.RoomAllocationsDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.RoomAmenityDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.RoomDetailsByStudentDTO;
import com.aristiec.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.hostel.RoomAllocations;
import com.aristiec.schoolmanagementsystem.modal.hostel.RoomDetail;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.hostel.RoomAllocationsRepository;
import com.aristiec.schoolmanagementsystem.repository.hostel.RoomDetailRepository;
import com.aristiec.schoolmanagementsystem.service.hostel.RoomAllocationsService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomAllocationsServiceImpl implements RoomAllocationsService {
    
    @Autowired
    private  RoomAllocationsRepository roomAllocationsRepository;
    @Autowired
    private RoomDetailRepository roomDetailRepository;
    @Autowired
    private StudentDetailsRepository studentDetailsRepository;
   
    @Override
    public String createRoomAllocation(RoomAllocationsDTO dto) {
        Long studentId = dto.getStudentId();
        Long roomId = dto.getRoomDetailId();

        StudentDetails student = studentDetailsRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        RoomDetail room = roomDetailRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

     int currentOccupancy = roomAllocationsRepository.countByRoomDetailRoomDetailIdAndIsVacatedFalse(roomId);

    if (currentOccupancy >= room.getStudentPerRoom()) {
        throw new IllegalStateException("Room is already full. Cannot allocate student.");
    }

        RoomAllocations allocation = new RoomAllocations();
        allocation.setStudentDetails(student);
        allocation.setRoomDetail(room);
        allocation.setVacated(dto.isVacated());
        allocation.setCreatedAt(LocalDate.now());
        allocation.setSchoolId(dto.getSchoolId());

        roomAllocationsRepository.save(allocation);
        return "Room allocated for student id: " + studentId + " room id: " + roomId;

    }
    @Override
    public RoomAllocationsDTO getRoomAllocationById(Long id) {
         RoomAllocations allocation = roomAllocationsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room allocation not found with ID: " + id));
        return convertToDto(allocation);
    }
   
    @Override
    public List<RoomAllocationsDTO> getAllAllocations() {
        List<RoomAllocations> allocations = roomAllocationsRepository.findAll();
        return allocations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    @Override
    public void deleteRoomAllocation(Long id) {
       if (!roomAllocationsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Room allocation not found with ID: " + id);
        }
        roomAllocationsRepository.deleteById(id);
    }
     
    private RoomAllocationsDTO convertToDto(RoomAllocations allocation) {
        RoomAllocationsDTO dto = new RoomAllocationsDTO();
        BeanUtils.copyProperties(allocation, dto);
        dto.setStudentId(allocation.getStudentDetails().getId());
        dto.setRoomDetailId(allocation.getRoomDetail().getRoomDetailId());
        return dto;
    }
    @Override
    public RoomDetailsByStudentDTO getRoomDetailsByStudentId(Long studentId) {
    RoomAllocations allocation = roomAllocationsRepository
        .findByStudentDetailsIdAndIsVacatedFalse(studentId)
        .orElseThrow(() -> new ResourceNotFoundException("No active room allocation found for student ID: " + studentId));

    RoomDetail room = allocation.getRoomDetail();


    int occupied = roomAllocationsRepository.countByRoomDetailRoomDetailIdAndIsVacatedFalse(room.getRoomDetailId());
    int capacity = room.getStudentPerRoom();

   
    RoomDetailsByStudentDTO dto = new RoomDetailsByStudentDTO();
    dto.setRoomNumber(room.getRoomNumber());
    dto.setRoomType(room.getRoomType());
    dto.setCapacity(capacity + " Sharing");
    dto.setCurrentOccupancy(occupied + "/" + capacity);
    dto.setCheckInDate(allocation.getCreatedAt());

    return dto;
    }
    @Override
    public List<RoomAmenityDTO> getAmenitiesByStudentId(Long studentId) {
        RoomAllocations allocation = roomAllocationsRepository
        .findByStudentDetailsIdAndIsVacatedFalse(studentId)
        .orElseThrow(() -> new ResourceNotFoundException("Room not found for studentId: " + studentId));

    return allocation.getRoomDetail().getAmenities()
        .stream()
        .map(amenity -> new RoomAmenityDTO(
            amenity.getAmenityName(),
            amenity.getDescription(),
            amenity.isAvailable()
        ))
        .toList();
    }


    
}

