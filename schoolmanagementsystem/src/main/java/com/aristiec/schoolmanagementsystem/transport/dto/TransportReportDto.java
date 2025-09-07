package com.aristiec.schoolmanagementsystem.transport.dto;

import lombok.Data;

@Data
public class TransportReportDto {
    private Long routeId;
    private String routeName;
    private Long vehicleId;
    private Long studentCount;
}