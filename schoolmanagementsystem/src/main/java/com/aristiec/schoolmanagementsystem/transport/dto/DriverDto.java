package com.aristiec.schoolmanagementsystem.transport.dto;

import lombok.Data;

@Data
public class DriverDto {
    private Long id;
    private String name;
    private String licenseNo;
    private String contact;
    private Long vehicleId;

}