package com.aristiec.schoolmanagementsystem.dto.staff;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.constant.enums.StaffDesignation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Data
public class StaffDTO {
    private String fullName;
    private StaffDesignation designation;
    private String email;
    private String phone;
    private String staffId;
    private double salary;
    private LocalDate dateOfJoining;
    private Long departmentId;
}
