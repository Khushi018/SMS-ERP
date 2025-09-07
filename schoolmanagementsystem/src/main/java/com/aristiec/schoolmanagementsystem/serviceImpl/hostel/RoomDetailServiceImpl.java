package com.aristiec.schoolmanagementsystem.serviceImpl.hostel;

import com.aristiec.schoolmanagementsystem.dto.hostel.RoomAmenityDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.RoomDetailDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.RoomWithRoommatesDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.RoommateDTO;
import com.aristiec.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.hostel.Hostel;
import com.aristiec.schoolmanagementsystem.modal.hostel.RoomAllocations;
import com.aristiec.schoolmanagementsystem.modal.hostel.RoomAmenity;
import com.aristiec.schoolmanagementsystem.modal.hostel.RoomDetail;
import com.aristiec.schoolmanagementsystem.repository.hostel.HostelRepository;
import com.aristiec.schoolmanagementsystem.repository.hostel.RoomDetailRepository;
import com.aristiec.schoolmanagementsystem.service.hostel.RoomDetailService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RoomDetailServiceImpl implements RoomDetailService {
      @Autowired
    private  RoomDetailRepository roomDetailRepository;
     @Autowired
    private  HostelRepository hostelRepository;
    @Autowired
    private  ModelMapper modelMapper;
    @Override
    public RoomDetailDTO createRoomDetail(RoomDetailDTO dto) {
        RoomDetail roomDetail = new RoomDetail(); 

    roomDetail.setRoomNumber(dto.getRoomNumber());
    roomDetail.setStudentPerRoom(dto.getStudentPerRoom());
    roomDetail.setRent(dto.getRent());
    roomDetail.setRoomType(dto.getRoomType());   
    roomDetail.setBlock(dto.getBlock());        
    roomDetail.setFloor(dto.getFloor());
    roomDetail.setCreatedAt(LocalDate.now());
    roomDetail.setUpdatedAt(dto.getUpdatedAt());

    if (dto.getHostelId() != null) {
        Hostel hostel = hostelRepository.findById(dto.getHostelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hostel not found with ID: " + dto.getHostelId()));
        roomDetail.setHostel(hostel);
    }

    // Set amenities
    if (dto.getAmenities() != null) {
        List<RoomAmenity> amenities = dto.getAmenities().stream().map(amenityDTO -> {
            RoomAmenity amenity = new RoomAmenity();
            amenity.setAmenityName(amenityDTO.getAmenityName());
            amenity.setDescription(amenityDTO.getDescription());
            amenity.setAvailable(amenityDTO.isAvailable());
            amenity.setRoomDetail(roomDetail);
            return amenity;
        }).toList();
        roomDetail.setAmenities(amenities);
    }

    RoomDetail savedRoomDetail = roomDetailRepository.save(roomDetail);

    RoomDetailDTO savedDto = new RoomDetailDTO();
    savedDto.setRoomDetailId(savedRoomDetail.getRoomDetailId());
    savedDto.setRoomNumber(savedRoomDetail.getRoomNumber());
    savedDto.setStudentPerRoom(savedRoomDetail.getStudentPerRoom());
    savedDto.setRent(savedRoomDetail.getRent());
    savedDto.setRoomType(savedRoomDetail.getRoomType());
    savedDto.setBlock(savedRoomDetail.getBlock());
    savedDto.setFloor(savedRoomDetail.getFloor());
    savedDto.setCreatedAt(savedRoomDetail.getCreatedAt());
    savedDto.setUpdatedAt(savedRoomDetail.getUpdatedAt());

    if (savedRoomDetail.getHostel() != null) {
        savedDto.setHostelId(savedRoomDetail.getHostel().getHostelId());
    }
     List<RoomAmenityDTO> amenityDTOs = savedRoomDetail.getAmenities().stream().map(amenity -> {
        RoomAmenityDTO adto = new RoomAmenityDTO();
        adto.setAmenityName(amenity.getAmenityName());
        adto.setDescription(amenity.getDescription());
        adto.setAvailable(amenity.isAvailable());
        return adto;
    }).toList();

    savedDto.setAmenities(amenityDTOs);


    return savedDto;
    }
    @Override
    public RoomDetailDTO getRoomDetailById(Long id) {
         RoomDetail roomDetail = roomDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room detail not found with ID: " + id));
        RoomDetailDTO dto = modelMapper.map(roomDetail, RoomDetailDTO.class);
        if (roomDetail.getHostel() != null) {
            dto.setHostelId(roomDetail.getHostel().getHostelId());
        }
        return dto;
    }
    @Override
    public List<RoomDetailDTO> getAllRoomDetails() {
      return roomDetailRepository.findAll()
                .stream()
                .map(roomDetail -> {
                    RoomDetailDTO dto = modelMapper.map(roomDetail, RoomDetailDTO.class);
                    if (roomDetail.getHostel() != null) {
                        dto.setHostelId(roomDetail.getHostel().getHostelId());
                    }
                    return dto;
                })
                .collect(Collectors.toList());

    }
    @Override
    public RoomWithRoommatesDTO getRoommatesByRoomNumber(String roomNumber) {
      RoomDetail room = roomDetailRepository.findByRoomNumber(roomNumber)
            .orElseThrow(() -> new RuntimeException("Room not found with number: " + roomNumber));

    RoomWithRoommatesDTO dto = new RoomWithRoommatesDTO();
    dto.setRoomNumber(room.getRoomNumber());
    dto.setRent(room.getRent());
    dto.setStudentPerRoom(room.getStudentPerRoom());

    List<RoommateDTO> roommates = room.getRoomAllocations().stream()
            .filter(alloc -> !alloc.isVacated())
            .map(alloc -> {
                StudentDetails student = alloc.getStudentDetails();
                RoommateDTO rm = new RoommateDTO();
                rm.setStudentId(student.getId());

                String fullName = Stream.of(student.getFirstName(), student.getMiddleName(), student.getLastName())
                        .filter(Objects::nonNull)
                        .filter(s -> !s.isBlank())
                        .collect(Collectors.joining(" "));
                rm.setFullName(fullName);

                rm.setStudentCode(student.getStudentCode());;
                rm.setVacated(alloc.isVacated());
                return rm;
            }).collect(Collectors.toList());

    dto.setRoommates(roommates);
    return dto;
        }
   

   
}