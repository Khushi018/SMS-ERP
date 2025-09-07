package com.aristiec.schoolmanagementsystem.serviceImpl.hostel;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.constant.enums.MaintenanceStatusEnum;
import com.aristiec.schoolmanagementsystem.dto.hostel.MaintenanceRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.MaintenanceRequestResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.hostel.MaintenanceStatusUpdateDTO;
import com.aristiec.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.hostel.MaintenanceRequest;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.hostel.MaintenanceRequestRepository;
import com.aristiec.schoolmanagementsystem.service.hostel.MaintenanceRequestService;

@Service
public class MaintenanceRequestServiceImpl implements MaintenanceRequestService{


     @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;

    @Autowired
    private StudentDetailsRepository studentDetailsRepository;

    @Override
    public String submitRequest(Long studentId,MaintenanceRequestDTO dto) {
        StudentDetails student = studentDetailsRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        MaintenanceRequest request = new MaintenanceRequest();
        request.setStudentDetails(student);
        request.setCategory(dto.getCategory());
        request.setDescription(dto.getDescription());
        request.setStatus(MaintenanceStatusEnum.PENDING);
        request.setRequestDate(LocalDate.now());

        maintenanceRequestRepository.save(request);
        return "Maintenance request submitted successfully.";
    }

    @Override
    public String updateStatus(MaintenanceStatusUpdateDTO dto) {
    MaintenanceRequest request = maintenanceRequestRepository.findById(dto.getRequestId())
        .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

     request.setStatus(dto.getStatus());

    if (dto.getStatus() == MaintenanceStatusEnum.COMPLETED || dto.getStatus() == MaintenanceStatusEnum.REJECTED) {
        request.setResolvedDate(LocalDate.now());
    }

    maintenanceRequestRepository.save(request);
    return "Status updated to " + dto.getStatus().name();
    }

    @Override
    public List<MaintenanceRequestResponseDTO> getStudentRequestHistory(Long studentId) {
            List<MaintenanceRequest> requests = maintenanceRequestRepository
        .findByStudentDetailsIdOrderByRequestDateDesc(studentId);

    return requests.stream().map(request -> {
        MaintenanceRequestResponseDTO dto = new MaintenanceRequestResponseDTO();
        dto.setRequestId(request.getId());
        dto.setRequestDate(request.getRequestDate());
        dto.setCategory(request.getCategory());
        dto.setDescription(request.getDescription());
        dto.setStatus(request.getStatus());
        return dto;
    }).toList();
    }
    
}
