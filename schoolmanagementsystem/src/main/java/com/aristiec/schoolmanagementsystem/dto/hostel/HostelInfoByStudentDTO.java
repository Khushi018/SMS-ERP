package com.aristiec.schoolmanagementsystem.dto.hostel;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostelInfoByStudentDTO {
     private String hostelName;
    private String hostelType;
    private String block;
    private String floor;
    private LocalDate allotmentDate;
}
