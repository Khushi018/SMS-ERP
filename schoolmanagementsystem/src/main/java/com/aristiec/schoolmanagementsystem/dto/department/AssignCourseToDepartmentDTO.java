package com.aristiec.schoolmanagementsystem.dto.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignCourseToDepartmentDTO {
    private Long courseId;
    private Long departmentId;
}
