package com.aristiec.schoolmanagementsystem.dto.hostel;

import com.aristiec.schoolmanagementsystem.constant.enums.MaintenanceStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceStatusUpdateDTO {
     private Long requestId;
    private MaintenanceStatusEnum status;
}
