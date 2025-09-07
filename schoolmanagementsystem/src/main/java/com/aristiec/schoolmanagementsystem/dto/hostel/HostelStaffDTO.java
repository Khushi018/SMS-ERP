package com.aristiec.schoolmanagementsystem.dto.hostel;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.constant.enums.HostelStaffDesignation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostelStaffDTO {
     private Long id;
    private String fullName;
    private String email;
    private String phone;
    private HostelStaffDesignation designation;
    private LocalDate joinedDate;
     private String officeLocation;
    private String workingHours;
    private Long hostelId;
   
}
