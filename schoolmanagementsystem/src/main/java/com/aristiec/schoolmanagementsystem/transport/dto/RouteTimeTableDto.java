package com.aristiec.schoolmanagementsystem.transport.dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class RouteTimeTableDto {
    private Long id;
    private Long routeId;
    private Long stoppageId;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
}