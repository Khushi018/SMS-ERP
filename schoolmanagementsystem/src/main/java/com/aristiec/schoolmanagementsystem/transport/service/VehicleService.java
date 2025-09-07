package com.aristiec.schoolmanagementsystem.transport.service;

import java.util.List;

import com.aristiec.schoolmanagementsystem.transport.dto.VehicleDto;

public interface VehicleService {
    VehicleDto createVehicle(VehicleDto dto);

    List<VehicleDto> getAllVehicles();

    VehicleDto getVehicleById(Long id);

    void deleteVehicle(Long id);

    List<VehicleDto> getVehiclesByRouteId(Long routeId);

}
