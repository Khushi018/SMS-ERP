package com.aristiec.schoolmanagementsystem.transport.serviceImpl;

import com.aristiec.schoolmanagementsystem.transport.dto.TransportReportDto;
import com.aristiec.schoolmanagementsystem.transport.modal.RouteVehicleAssignment;
import com.aristiec.schoolmanagementsystem.transport.modal.StudentTransportAssignment;
import com.aristiec.schoolmanagementsystem.transport.repository.RouteVehicleAssignmentRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.StudentTransportAssignmentRepository;
import com.aristiec.schoolmanagementsystem.transport.service.TransportReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransportReportServiceImpl implements TransportReportService {

    @Autowired
    private RouteVehicleAssignmentRepository rvRepo;
    @Autowired
    private StudentTransportAssignmentRepository stRepo;

    @Override
    public List<TransportReportDto> getReport() {
        return rvRepo.findAll().stream().map(rva -> {
            TransportReportDto dto = new TransportReportDto();
            dto.setRouteId(rva.getRoute().getId());
            dto.setRouteName(rva.getRoute().getName());
            dto.setVehicleId(rva.getVehicle().getId());
            long count = stRepo.findByRouteId(rva.getRoute().getId()).size();
            dto.setStudentCount(count);
            return dto;
        }).collect(Collectors.toList());
    }
}
