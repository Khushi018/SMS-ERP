package com.aristiec.schoolmanagementsystem.dto.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Data
public class DepartmentDTO {
    private String name;
    private String description;
}
