package com.aristiec.schoolmanagementsystem.modal.notification;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TransportNotification {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
   private LocalDateTime createdAt;
    private Long routeId;
    private Long vehicleId;
    
    @PrePersist
public void prePersist() {
    this.createdAt = LocalDateTime.now();
}

}
