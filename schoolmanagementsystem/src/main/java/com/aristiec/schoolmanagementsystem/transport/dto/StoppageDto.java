package com.aristiec.schoolmanagementsystem.transport.dto;

import lombok.Data;

@Data
public class StoppageDto {
    private Long id;
    private Long routeId;
    private String name;
    private String arrivalTime;
    private Double distance;
    private String duration;
    private Boolean dropOff;
}