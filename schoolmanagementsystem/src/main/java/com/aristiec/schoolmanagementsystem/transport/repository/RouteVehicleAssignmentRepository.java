package com.aristiec.schoolmanagementsystem.transport.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.transport.modal.RouteVehicleAssignment;

public interface RouteVehicleAssignmentRepository extends JpaRepository<RouteVehicleAssignment, Long> {
    List<RouteVehicleAssignment> findByRouteId(Long routeId);

    List<RouteVehicleAssignment> findByVehicleId(Long vehicleId);

    Optional<RouteVehicleAssignment> findByRouteIdAndVehicleId(Long routeId, Long vehicleId);
}
