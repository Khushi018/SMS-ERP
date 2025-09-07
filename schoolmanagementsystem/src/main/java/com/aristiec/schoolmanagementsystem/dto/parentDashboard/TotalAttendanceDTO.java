package com.aristiec.schoolmanagementsystem.dto.parentDashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalAttendanceDTO {
     private long totalDays;
    private long presentDays;
    private double attendancePercentage;
}
