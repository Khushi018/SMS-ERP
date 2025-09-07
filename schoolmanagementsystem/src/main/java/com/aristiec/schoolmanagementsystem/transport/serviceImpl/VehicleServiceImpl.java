package com.aristiec.schoolmanagementsystem.transport.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.transport.dto.VehicleDto;
import com.aristiec.schoolmanagementsystem.transport.modal.Route;
import com.aristiec.schoolmanagementsystem.transport.modal.Vehicle;
import com.aristiec.schoolmanagementsystem.transport.repository.RouteRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.VehicleRepository;
import com.aristiec.schoolmanagementsystem.transport.service.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository repo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public VehicleDto createVehicle(VehicleDto dto) {
        Vehicle entity = mapper.map(dto, Vehicle.class);

        if (dto.getRouteId() != null) {
            Route route = routeRepository.findById(dto.getRouteId())
                    .orElseThrow(() -> new RuntimeException("Route not found"));
            entity.setRoute(route);
        }

        entity = repo.save(entity);
        VehicleDto responseDto = mapper.map(entity, VehicleDto.class);
        responseDto.setRouteId(entity.getRoute().getId());
        return responseDto;
    }

    @Override
    public List<VehicleDto> getAllVehicles() {
        return repo.findAll()
                .stream()
                .map(v -> mapper.map(v, VehicleDto.class))
                .toList();
    }

    @Override
    public VehicleDto getVehicleById(Long id) {
        Vehicle v = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        return mapper.map(v, VehicleDto.class);
    }

    @Override
    public void deleteVehicle(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<VehicleDto> getVehiclesByRouteId(Long routeId) {
        List<Vehicle> vehicles = repo.findByRouteId(routeId);
        return vehicles.stream()
                .map(v -> mapper.map(v, VehicleDto.class))
                .toList();
    }

}
