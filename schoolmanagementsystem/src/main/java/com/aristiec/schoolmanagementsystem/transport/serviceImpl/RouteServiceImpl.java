package com.aristiec.schoolmanagementsystem.transport.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.transport.dto.RouteDTO;
import com.aristiec.schoolmanagementsystem.transport.dto.VehicleDto;
import com.aristiec.schoolmanagementsystem.transport.modal.Route;
import com.aristiec.schoolmanagementsystem.transport.modal.RouteVehicleAssignment;
import com.aristiec.schoolmanagementsystem.transport.modal.Vehicle;
import com.aristiec.schoolmanagementsystem.transport.repository.RouteRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.RouteVehicleAssignmentRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.VehicleRepository;
import com.aristiec.schoolmanagementsystem.transport.service.RouteService;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository repo;

    @Autowired
    private RouteVehicleAssignmentRepository routeVehicleAssignmentRepo;

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public RouteDTO createRoute(RouteDTO dto) {
        Route entity = mapper.map(dto, Route.class);
        entity = repo.save(entity);
        return mapper.map(entity, RouteDTO.class);
    }

    @Override
    public List<RouteDTO> getAllRoutes() {
        return repo.findAll()
                .stream()
                .map(r -> mapper.map(r, RouteDTO.class))
                .toList();
    }

    @Override
    public RouteDTO getRouteById(Long id) {
        Route r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found"));
        return mapper.map(r, RouteDTO.class);
    }

    @Override
    public void deleteRoute(Long id) {
        repo.deleteById(id);
    }

}
