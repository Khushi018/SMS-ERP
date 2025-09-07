package com.aristiec.schoolmanagementsystem.dto.parentDashboard;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamScheduleDTO {
    private Long examScheduleId;
    private String description;
    private LocalDate startDate;
    private List<LocalDateTime> dateTimeList; 
}
