package com.aristiec.schoolmanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDTO {
    private String fullName;
    private String fatherName;
    private String contactNumber;
    private String email;
    private String address;
    private String gender;
    private String course;
    private String year;
     private String section;
    private String category;

}
