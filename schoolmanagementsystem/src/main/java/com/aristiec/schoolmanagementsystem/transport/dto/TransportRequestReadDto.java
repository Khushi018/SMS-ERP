package com.aristiec.schoolmanagementsystem.transport.dto;

import lombok.Data;

@Data
public class TransportRequestReadDto {
    private Long id;

    private String name;

    private Long studentId;

    private Long number;

    private String email;

    private String requestType;

    private String startDate;

    private String location;

    private String requestStatus;

    private String pincode;

    private String landmark;

    private String[] day;
}
