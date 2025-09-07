package com.aristiec.schoolmanagementsystem.transport.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.transport.dto.RouteVehicleAssignmentDto;
import com.aristiec.schoolmanagementsystem.transport.modal.Route;
import com.aristiec.schoolmanagementsystem.transport.modal.RouteVehicleAssignment;
import com.aristiec.schoolmanagementsystem.transport.modal.Vehicle;
import com.aristiec.schoolmanagementsystem.transport.repository.RouteRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.RouteVehicleAssignmentRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.VehicleRepository;
import com.aristiec.schoolmanagementsystem.transport.service.RouteVehicleAssignmentService;

@Service
public class RouteVehicleAssignmentServiceImpl implements RouteVehicleAssignmentService {

    @Autowired
    private RouteVehicleAssignmentRepository repo;

    @Autowired
    private RouteRepository routeRepo;

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public RouteVehicleAssignmentDto assignVehicle(RouteVehicleAssignmentDto dto) {
        if (repo.findByRouteIdAndVehicleId(dto.getRouteId(), dto.getVehicleId()).isPresent()) {
            throw new RuntimeException("Vehicle already assigned to this route");
        }

        Route route = routeRepo.findById(dto.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found"));

        Vehicle vehicle = vehicleRepo.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        RouteVehicleAssignment entity = new RouteVehicleAssignment();
        entity.setRoute(route);
        entity.setVehicle(vehicle);

        entity = repo.save(entity);
        return mapper.map(entity, RouteVehicleAssignmentDto.class);
    }

    @Override
    public List<RouteVehicleAssignmentDto> getAssignmentsByRoute(Long routeId) {
        return repo.findByRouteId(routeId).stream()
                .map(rva -> mapper.map(rva, RouteVehicleAssignmentDto.class))
                .toList();
    }

    @Override
    public List<RouteVehicleAssignmentDto> getAssignmentsByVehicle(Long vehicleId) {
        return repo.findByVehicleId(vehicleId).stream()
                .map(rva -> mapper.map(rva, RouteVehicleAssignmentDto.class))
                .toList();
    }

    @Override
    public void deleteAssignment(Long id) {
        repo.deleteById(id);
    }
}
