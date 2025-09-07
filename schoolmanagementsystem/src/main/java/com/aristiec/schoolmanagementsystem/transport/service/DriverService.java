package com.aristiec.schoolmanagementsystem.transport.service;

import java.util.List;

import com.aristiec.schoolmanagementsystem.transport.dto.DriverDto;

public interface DriverService {
    DriverDto createDriver(DriverDto dto);

    List<DriverDto> getAllDrivers();

    DriverDto getDriverById(Long id);

    void deleteDriver(Long id);

    List<DriverDto> getDriversByVehicleId(Long vehicleId); // Add this line
}
