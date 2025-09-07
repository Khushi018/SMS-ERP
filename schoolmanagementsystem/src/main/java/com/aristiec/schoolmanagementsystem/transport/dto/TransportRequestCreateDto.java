package com.aristiec.schoolmanagementsystem.transport.dto;

import lombok.Data;

@Data
public class TransportRequestCreateDto {
    private String name;

    private Long studentId;

    private Long number;

    private String email;

    private Long emergencyNo1;

    private Long emergencyNo2;

    private String requestType;

    private String startDate; // In dd-MM-yyyy format

    private String location;

    private String pincode;

    private String landmark;

    private String[] day;
}
