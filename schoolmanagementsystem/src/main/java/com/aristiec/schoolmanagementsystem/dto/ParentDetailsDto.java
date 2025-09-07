package com.aristiec.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentDetailsDto {
    private Long id;

    private String fatherName;
    private String fatherOccupation;
    private String fatherContact;
    private String email;

    private String motherName;
    private String motherOccupation;
    private String motherContact;

    private String guardianName;
    private String guardianContact;
    private String guardianRelation;

}
