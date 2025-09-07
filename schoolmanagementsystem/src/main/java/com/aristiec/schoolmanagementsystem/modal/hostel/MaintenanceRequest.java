package com.aristiec.schoolmanagementsystem.modal.hostel;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.constant.enums.MaintenanceCategoryEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.MaintenanceStatusEnum;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "maintenance_requests")
public class MaintenanceRequest {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MaintenanceCategoryEnum category;

    private String description;

    @Enumerated(EnumType.STRING)
    private MaintenanceStatusEnum status;

    private LocalDate requestDate;
    private LocalDate resolvedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private StudentDetails studentDetails;

    @PrePersist
    public void onCreate() {
        this.requestDate = LocalDate.now();
        if (this.status == null) {
            this.status = MaintenanceStatusEnum.PENDING;
        }
    }

}
