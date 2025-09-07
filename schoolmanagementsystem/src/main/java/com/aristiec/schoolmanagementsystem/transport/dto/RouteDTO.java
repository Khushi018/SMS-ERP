package com.aristiec.schoolmanagementsystem.transport.dto;

import lombok.Data;

@Data
public class RouteDTO {
    private Long id;
    private String name;
    private String startPoint;
    private String endPoint;
    private String description;
}