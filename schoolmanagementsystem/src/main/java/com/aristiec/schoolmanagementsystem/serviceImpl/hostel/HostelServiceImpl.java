package com.aristiec.schoolmanagementsystem.serviceImpl.hostel;

import com.aristiec.schoolmanagementsystem.dto.hostel.HostelDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.HostelInfoByStudentDTO;
import com.aristiec.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.aristiec.schoolmanagementsystem.modal.hostel.Hostel;
import com.aristiec.schoolmanagementsystem.modal.hostel.RoomAllocations;
import com.aristiec.schoolmanagementsystem.modal.hostel.RoomDetail;
import com.aristiec.schoolmanagementsystem.repository.hostel.HostelRepository;
import com.aristiec.schoolmanagementsystem.repository.hostel.RoomAllocationsRepository;
import com.aristiec.schoolmanagementsystem.service.hostel.HostelService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HostelServiceImpl implements HostelService {
      @Autowired
    private HostelRepository hostelRepository;
     @Autowired
    private ModelMapper modelMapper;

     @Autowired
    private RoomAllocationsRepository roomAllocationsRepository;
    
    @Override
    public HostelDTO createHostel(HostelDTO hostelDTO) {
        Hostel hostel = modelMapper.map(hostelDTO, Hostel.class);
        hostel.setCreatedAt(LocalDate.now());
        Hostel saved = hostelRepository.save(hostel);
        return modelMapper.map(saved, HostelDTO.class);
    }

    @Override
    public HostelDTO getHostelById(Long id) {
        Hostel hostel = hostelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hostel not found with ID: " + id));
        return modelMapper.map(hostel, HostelDTO.class);
    }

    @Override
    public List<HostelDTO> getAllHostels() {
         return hostelRepository.findAll()
                .stream()
                .map(hostel -> modelMapper.map(hostel, HostelDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public HostelInfoByStudentDTO getHostelByStudentId(Long studentId) {
         RoomAllocations allocation = roomAllocationsRepository.findByStudentDetailsIdAndIsVacatedFalse(studentId)
        .orElseThrow(() -> new ResourceNotFoundException("Active room allocation not found for student"));

    RoomDetail room = allocation.getRoomDetail();
    Hostel hostel = room.getHostel();

    HostelInfoByStudentDTO dto = new HostelInfoByStudentDTO();
    dto.setHostelName(hostel.getHostelName());
    dto.setHostelType(hostel.getHostelType());
    dto.setBlock(room.getBlock());
    dto.setFloor(room.getFloor());
    dto.setAllotmentDate(allocation.getCreatedAt());

    return dto;

    }


    
}