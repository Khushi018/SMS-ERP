package com.aristiec.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter @AllArgsConstructor @NoArgsConstructor
@Data
public class SubjectDTO {
    private Long id;
    private String name;
    private String code;
    private Integer semester;
    private Integer credit;
    private Boolean active;
    private Long courseId;
}
