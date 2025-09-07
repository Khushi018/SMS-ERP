package com.aristiec.schoolmanagementsystem.dto.subjectAssignment;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectAssiDTO {
    private String subjectName;
    private String subjectCode;
    private List<AssiDTO> assignments;
}
