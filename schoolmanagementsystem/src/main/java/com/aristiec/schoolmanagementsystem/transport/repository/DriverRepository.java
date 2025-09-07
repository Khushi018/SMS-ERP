package com.aristiec.schoolmanagementsystem.transport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.transport.modal.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByVehicleId(Long vehicleId);

}
