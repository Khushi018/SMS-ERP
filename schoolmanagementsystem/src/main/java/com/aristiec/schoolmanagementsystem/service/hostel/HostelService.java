package com.aristiec.schoolmanagementsystem.service.hostel;

import com.aristiec.schoolmanagementsystem.dto.hostel.HostelDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.HostelInfoByStudentDTO;
import com.aristiec.schoolmanagementsystem.modal.hostel.Hostel;

import java.util.List;

public interface HostelService {
    HostelDTO createHostel(HostelDTO hostelDTO);
    HostelDTO getHostelById(Long id);
    List<HostelDTO> getAllHostels();
    HostelInfoByStudentDTO getHostelByStudentId(Long studentId);
}
