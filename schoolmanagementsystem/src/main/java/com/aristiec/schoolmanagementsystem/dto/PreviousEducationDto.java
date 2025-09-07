package com.aristiec.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreviousEducationDto {
    private Long id;
    private String lastSchoolOrCollege;
    private String qualification;
    private String boardOrUniversity;
    private Integer passingYear;
    private Double percentage;
}
