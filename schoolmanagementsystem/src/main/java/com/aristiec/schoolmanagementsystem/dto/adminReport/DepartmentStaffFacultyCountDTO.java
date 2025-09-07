package com.aristiec.schoolmanagementsystem.dto.adminReport;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentStaffFacultyCountDTO {
    private String departmentName;
    private long staffCount;
    private long facultyCount;

}
