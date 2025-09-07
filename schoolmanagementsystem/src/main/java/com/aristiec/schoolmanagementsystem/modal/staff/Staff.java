package com.aristiec.schoolmanagementsystem.modal.staff;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.constant.enums.StaffDesignation;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String staffId;


    private LocalDate dataOfJoining;
    private double salary;


    @Enumerated(EnumType.STRING)
    private StaffDesignation designation;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
