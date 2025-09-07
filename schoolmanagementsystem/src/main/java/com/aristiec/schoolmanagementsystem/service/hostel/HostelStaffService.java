package com.aristiec.schoolmanagementsystem.service.hostel;

import java.util.List;

import com.aristiec.schoolmanagementsystem.dto.hostel.HostelIMPStaffDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.HostelStaffDTO;

public interface HostelStaffService {
     HostelStaffDTO addStaff(HostelStaffDTO dto);
     List<HostelStaffDTO> getAllByHostelId(Long hostelId);
     HostelStaffDTO updateStaff(Long id, HostelStaffDTO dto);
     void deleteStaff(Long id);
     List<HostelIMPStaffDTO> getImportantStaffByStudentId(Long studentId);
}
