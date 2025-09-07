package com.aristiec.schoolmanagementsystem.dto.parentDashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Data
public class StudentDTO {

     private Long id;
     private String firstName;
     private String lastName;
     private String email;
     private String courseName;
     
    
}
