package com.aristiec.schoolmanagementsystem.dto.parentDashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectWiseComparisonDTO {
     private String subjectName;
    private double studentMarks;
    private double classAverage;
}
