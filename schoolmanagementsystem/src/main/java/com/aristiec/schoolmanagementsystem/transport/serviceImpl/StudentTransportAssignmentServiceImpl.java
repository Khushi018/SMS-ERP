package com.aristiec.schoolmanagementsystem.transport.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.transport.dto.RouteDTO;
import com.aristiec.schoolmanagementsystem.transport.dto.StoppageDto;
import com.aristiec.schoolmanagementsystem.transport.dto.StudentTransportAssignmentDto;
import com.aristiec.schoolmanagementsystem.transport.dto.VehicleDto;
import com.aristiec.schoolmanagementsystem.transport.modal.Route;
import com.aristiec.schoolmanagementsystem.transport.modal.Stoppage;
import com.aristiec.schoolmanagementsystem.transport.modal.StudentTransportAssignment;
import com.aristiec.schoolmanagementsystem.transport.modal.Vehicle;
import com.aristiec.schoolmanagementsystem.transport.repository.RouteRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.StoppageRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.StudentStoppageDropOffRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.StudentTransportAssignmentRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.VehicleRepository;
import com.aristiec.schoolmanagementsystem.transport.service.StudentTransportAssignmentService;

@Service
public class StudentTransportAssignmentServiceImpl implements StudentTransportAssignmentService {

    @Autowired
    private StudentTransportAssignmentRepository repo;

    @Autowired
    private StudentDetailsRepository studentRepo;

    @Autowired
    private RouteRepository routeRepo;

    @Autowired
    private StoppageRepository stoppageRepo;

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private StudentStoppageDropOffRepository dropOffRepo;

    @Override
    public StudentTransportAssignmentDto assign(StudentTransportAssignmentDto dto) {
        StudentDetails student = studentRepo.findByStudentCode(dto.getStudentCode())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Route route = routeRepo.findById(dto.getRoute().getId())
                .orElseThrow(() -> new RuntimeException("Route not found"));

        Vehicle vehicle = vehicleRepo.findById(dto.getVehicle().getId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        StudentTransportAssignment assignment = new StudentTransportAssignment();
        assignment.setStudent(student);
        assignment.setRoute(route);
        assignment.setVehicle(vehicle);

        StudentTransportAssignment saved = repo.save(assignment);
        StudentTransportAssignmentDto responseDto = mapper.map(saved, StudentTransportAssignmentDto.class);

        // Include all stoppages under this route
        List<StoppageDto> stoppages = stoppageRepo.findByRouteId(route.getId())
                .stream()
                .map(s -> mapper.map(s, StoppageDto.class))
                .toList();

        responseDto.setAllRouteStoppages(stoppages);

        return responseDto;
    }

    @Override
    public List<StudentTransportAssignmentDto> getByRoute(Long routeId) {
        return repo.findByRouteId(routeId).stream()
                .map(e -> mapper.map(e, StudentTransportAssignmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentTransportAssignmentDto> getByStudentCode(String studentCode) {
        return repo.findByStudent_StudentCode(studentCode).stream().map(e -> {
            StudentTransportAssignmentDto dto = mapper.map(e, StudentTransportAssignmentDto.class);

            Long routeId = e.getRoute().getId();
            Long assignmentId = e.getId();

            // Get all stoppages for the route
            List<StoppageDto> stoppages = stoppageRepo.findByRouteId(routeId)
                    .stream()
                    .map(s -> {
                        StoppageDto stoppageDto = mapper.map(s, StoppageDto.class);

                        // Check if this stoppage is the assigned drop-off for this student
                        boolean isDropOff = dropOffRepo.existsByAssignment_IdAndStoppage_Id(assignmentId, s.getId());
                        stoppageDto.setDropOff(isDropOff);

                        return stoppageDto;
                    }).toList();

            dto.setAllRouteStoppages(stoppages);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void remove(Long id) {
        repo.deleteById(id);
    }
}