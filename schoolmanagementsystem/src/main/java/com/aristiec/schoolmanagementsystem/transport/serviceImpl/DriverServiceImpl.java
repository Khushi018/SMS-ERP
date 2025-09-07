package com.aristiec.schoolmanagementsystem.transport.serviceImpl;

import java.util.List;

import com.aristiec.schoolmanagementsystem.transport.modal.Vehicle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.transport.dto.DriverDto;
import com.aristiec.schoolmanagementsystem.transport.modal.Driver;
import com.aristiec.schoolmanagementsystem.transport.repository.DriverRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.VehicleRepository;
import com.aristiec.schoolmanagementsystem.transport.service.DriverService;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository repo;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public DriverDto createDriver(DriverDto dto) {
        Driver driver = mapper.map(dto, Driver.class);

        if (dto.getVehicleId() != null) {
            Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found"));
            driver.setVehicle(vehicle);
        }

        driver = repo.save(driver);
        return mapper.map(driver, DriverDto.class);
    }

    @Override
    public List<DriverDto> getAllDrivers() {
        return repo.findAll().stream()
                .map(d -> mapper.map(d, DriverDto.class))
                .toList();
    }

    @Override
    public DriverDto getDriverById(Long id) {
        Driver d = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        return mapper.map(d, DriverDto.class);
    }

    @Override
    public void deleteDriver(Long id) {
        repo.deleteById(id);
    }

    public List<DriverDto> getDriversByVehicleId(Long vehicleId) {
        return repo.findByVehicleId(vehicleId)
                .stream()
                .map(driver -> mapper.map(driver, DriverDto.class))
                .toList();
    }
}
