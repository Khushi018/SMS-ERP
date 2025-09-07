package com.aristiec.schoolmanagementsystem.dto.hostel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostelIMPStaffDTO {
     private String fullName;
    private String email;
    private String phone;
    private String designation;
    private String officeLocation;
    private String workingHours;
}
