package com.aristiec.schoolmanagementsystem.dto.hostel;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.constant.enums.MaintenanceCategoryEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.MaintenanceStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceRequestResponseDTO {
     private Long requestId;
    private LocalDate requestDate;
    private MaintenanceCategoryEnum category;
    private String description;
    private MaintenanceStatusEnum status;
}
