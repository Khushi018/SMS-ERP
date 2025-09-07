package com.aristiec.schoolmanagementsystem.dto.hostel;

import com.aristiec.schoolmanagementsystem.constant.enums.MaintenanceCategoryEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceRequestDTO {
    private MaintenanceCategoryEnum category;
    private String description;
}
