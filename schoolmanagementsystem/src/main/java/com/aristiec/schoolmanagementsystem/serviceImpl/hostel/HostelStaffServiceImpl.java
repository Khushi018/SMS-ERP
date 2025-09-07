package com.aristiec.schoolmanagementsystem.serviceImpl.hostel;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.constant.enums.HostelStaffDesignation;
import com.aristiec.schoolmanagementsystem.dto.hostel.HostelIMPStaffDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.HostelStaffDTO;
import com.aristiec.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.hostel.Hostel;
import com.aristiec.schoolmanagementsystem.modal.hostel.HostelStaff;
import com.aristiec.schoolmanagementsystem.modal.hostel.RoomAllocations;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.hostel.HostelRepository;
import com.aristiec.schoolmanagementsystem.repository.hostel.HostelStaffRepository;
import com.aristiec.schoolmanagementsystem.service.hostel.HostelStaffService;

@Service
public class HostelStaffServiceImpl implements HostelStaffService {
      
    @Autowired
    private  HostelStaffRepository staffRepo;
    @Autowired
    private  HostelRepository hostelRepo;
    @Autowired
    private StudentDetailsRepository studentDetailsRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public HostelStaffDTO addStaff(HostelStaffDTO dto) {
        Hostel hostel = hostelRepo.findById(dto.getHostelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hostel not found"));

        HostelStaff staff = mapper.map(dto, HostelStaff.class);
        staff.setHostel(hostel);
        staff.setJoinedDate(LocalDate.now());
        staff.setActive(true);

        return mapper.map(staffRepo.save(staff), HostelStaffDTO.class);
    }

    @Override
    public List<HostelStaffDTO> getAllByHostelId(Long hostelId) {
       return staffRepo.findByHostel_HostelId(hostelId)
                .stream()
                .map(staff -> mapper.map(staff, HostelStaffDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public HostelStaffDTO updateStaff(Long id, HostelStaffDTO dto) {
        HostelStaff staff = staffRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found"));

        mapper.map(dto, staff);
        return mapper.map(staffRepo.save(staff), HostelStaffDTO.class);
    }

    @Override
    public void deleteStaff(Long id) {
        if (!staffRepo.existsById(id)) {
            throw new ResourceNotFoundException("Staff not found with ID: " + id);
        }
        staffRepo.deleteById(id);
    }

    @Override
    public List<HostelIMPStaffDTO> getImportantStaffByStudentId(Long studentId) {
           StudentDetails student = studentDetailsRepository.findById(studentId)
        .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

    RoomAllocations allocation = student.getRoomAllocations();
    if (allocation == null || allocation.getRoomDetail() == null || allocation.getRoomDetail().getHostel() == null) {
        throw new ResourceNotFoundException("No hostel assigned to the student.");
    }

    Hostel hostel = allocation.getRoomDetail().getHostel();

    List<HostelStaffDesignation> importantDesignations = List.of(
        HostelStaffDesignation.HOSTEL_WARDEN,
        HostelStaffDesignation.ASSISTANT_WARDEN,
        HostelStaffDesignation.HOSTEL_SUPERVISOR
    );

    List<HostelStaff> staffList = staffRepo
        .findByHostel_HostelIdAndDesignationIn(hostel.getHostelId(), importantDesignations);

    return staffList.stream().map(staff -> {
        HostelIMPStaffDTO dto = new HostelIMPStaffDTO();
        dto.setFullName(staff.getFullName());
        dto.setEmail(staff.getEmail());
        dto.setPhone(staff.getPhone());
        dto.setDesignation(staff.getDesignation().toString());
        dto.setOfficeLocation(staff.getOfficeLocation());
        dto.setWorkingHours(staff.getWorkingHours());
        return dto;
    }).collect(Collectors.toList());

    }
    
}
