package com.aristiec.schoolmanagementsystem.dto.hostel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoommateDTO {
     private Long studentId;
    private String fullName;
    private String studentCode;
    private boolean isVacated;
}
