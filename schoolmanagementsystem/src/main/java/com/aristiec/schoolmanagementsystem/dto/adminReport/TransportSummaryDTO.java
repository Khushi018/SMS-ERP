package com.aristiec.schoolmanagementsystem.dto.adminReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportSummaryDTO {
     private long totalDrivers;
    private long totalStudentsUsingTransport;
    private long totalStoppages;
    private long totalVehicles;
    private long totalRoutes;
}
