package com.aristiec.schoolmanagementsystem.transport.service;

import java.util.List;

import com.aristiec.schoolmanagementsystem.transport.dto.RouteVehicleAssignmentDto;

public interface RouteVehicleAssignmentService {
    RouteVehicleAssignmentDto assignVehicle(RouteVehicleAssignmentDto dto);

    List<RouteVehicleAssignmentDto> getAssignmentsByRoute(Long routeId);

    List<RouteVehicleAssignmentDto> getAssignmentsByVehicle(Long vehicleId);

    void deleteAssignment(Long id);
}