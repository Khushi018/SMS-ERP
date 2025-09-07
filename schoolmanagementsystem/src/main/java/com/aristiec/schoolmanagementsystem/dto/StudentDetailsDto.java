package com.aristiec.schoolmanagementsystem.dto;

import java.time.LocalDate;
import java.util.List;

import com.aristiec.schoolmanagementsystem.constant.enums.GenderEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.LevelEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetailsDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private GenderEnum gender;
    private String bloodGroup;
    private String nationality;
    private String religion;
    private String aadharNumber;
    private String email;
    private String mobileNumber;
    private String profilePhotoUrl;
    private LevelEnum level;
    private String year;
     private Integer semester;
    private Boolean active;
    private CourseDTO course;
    private ParentDetailsDto parentDetails;
    private List<AddressDto> address;
    private List<PreviousEducationDto> previousEducation;
}
