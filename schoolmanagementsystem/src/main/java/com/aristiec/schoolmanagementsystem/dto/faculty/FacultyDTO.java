package com.aristiec.schoolmanagementsystem.dto.faculty;

import java.time.LocalDate;
import java.util.List;

import org.aspectj.weaver.Position;

import com.aristiec.schoolmanagementsystem.constant.enums.FacultyDesignation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyDTO {
     private Long id;
    private String fullName;
    private String facultyId;
    private String email;
    private String phoneNumber;
    private FacultyDesignation position;
    private int experience;
    private String qualification;
    private double salary;
    private LocalDate joiningDate;
    private Long departmentId;
    private List<Long> courseIds;
    
}
