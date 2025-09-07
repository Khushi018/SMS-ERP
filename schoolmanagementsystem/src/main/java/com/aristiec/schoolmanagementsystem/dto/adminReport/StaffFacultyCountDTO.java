package com.aristiec.schoolmanagementsystem.dto.adminReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffFacultyCountDTO {
     private long totalStaff;
    private long totalFaculty;
}
