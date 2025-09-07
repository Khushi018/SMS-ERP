package com.aristiec.schoolmanagementsystem.transport.dto;

import lombok.Data;

@Data
public class VehicleDto {
    private Long id;
    private Long routeId;
    private String number;
    private String type;
    private Integer capacity;
}