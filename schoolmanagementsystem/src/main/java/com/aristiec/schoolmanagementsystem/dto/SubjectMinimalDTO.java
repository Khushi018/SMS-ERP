package com.aristiec.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectMinimalDTO {
     private String name;
     private String code;
     private Integer credit;
    private Integer semester;
    

}
