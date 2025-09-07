package com.aristiec.schoolmanagementsystem.modal.hostel;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.constant.enums.HostelStaffDesignation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "hostel_staff")
public class HostelStaff {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private HostelStaffDesignation designation;

    private LocalDate joinedDate;
    private Boolean active = true;
    
    private String officeLocation; 

    private String workingHours;

    @ManyToOne
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;
}
