package com.aristiec.schoolmanagementsystem.dto.timetable;

import com.aristiec.schoolmanagementsystem.constant.enums.DayOfWeek;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimetableSlotResponseDTO {
    private Long id;
    private String startTime;
    private String endTime;
    private String subjectName;
    private String facultyName;
    private String code;
}
