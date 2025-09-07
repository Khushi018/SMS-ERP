package com.aristiec.schoolmanagementsystem.transport.dto;

import lombok.Data;

@Data
public class RouteVehicleAssignmentDto {
    private Long id;
    private Long routeId;
    private Long vehicleId;
}