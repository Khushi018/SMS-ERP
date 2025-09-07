package com.aristiec.schoolmanagementsystem.dto.adminReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Data
public class AdminReportDTO {
     private long totalStudents;
 private long totalTeachingStaff;
    private long totalNonTeachingStaff;
    private long totalDepartments;
    private long totalCourses;
}
