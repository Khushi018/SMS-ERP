package com.aristiec.schoolmanagementsystem.transport.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.transport.modal.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByNumber(String number);

    List<Vehicle> findByRouteId(Long routeId);

}
