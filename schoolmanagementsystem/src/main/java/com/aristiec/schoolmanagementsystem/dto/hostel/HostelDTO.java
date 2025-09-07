package com.aristiec.schoolmanagementsystem.dto.hostel;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostelDTO {
     private Long hostelId;
    private String hostelName;
    private String hostelType;
    private String otherInfo;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
