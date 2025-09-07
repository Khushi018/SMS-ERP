package com.aristiec.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Data
public class CourseDTO {
    private Long id;
    private String courseName;
    private String courseCode;
    private Integer durationInYears;
    private Double totalFees;
    private String description;
    private Boolean isSemesterSystem;
    private Boolean active;
}
