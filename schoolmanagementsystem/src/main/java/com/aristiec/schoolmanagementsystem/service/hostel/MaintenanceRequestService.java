package com.aristiec.schoolmanagementsystem.service.hostel;

import java.util.List;

import com.aristiec.schoolmanagementsystem.dto.hostel.MaintenanceRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.MaintenanceRequestResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.MaintenanceStatusUpdateDTO;

public interface MaintenanceRequestService {
     String submitRequest(Long studentId, MaintenanceRequestDTO dto);
    String updateStatus(MaintenanceStatusUpdateDTO dto);
    List<MaintenanceRequestResponseDTO> getStudentRequestHistory(Long studentId);
}
