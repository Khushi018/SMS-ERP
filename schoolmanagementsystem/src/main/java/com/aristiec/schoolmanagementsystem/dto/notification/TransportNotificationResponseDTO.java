package com.aristiec.schoolmanagementsystem.dto.notification;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportNotificationResponseDTO {
     private Long id;
    private String title;
    private String description;
    private Long routeId;
    private Long vehicleId;
     private LocalDateTime createdAt;
}
