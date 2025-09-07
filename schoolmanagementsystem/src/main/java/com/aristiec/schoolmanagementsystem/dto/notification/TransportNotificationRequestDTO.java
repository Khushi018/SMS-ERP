package com.aristiec.schoolmanagementsystem.dto.notification;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportNotificationRequestDTO {
    private String title;
    private String description;
    private Long routeId;
    private Long vehicleId;

}
