package com.aristiec.schoolmanagementsystem.dto.timetable;

import com.aristiec.schoolmanagementsystem.constant.enums.DayOfWeek;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimetableSlotDTO {
    private String startTime;
    private String endTime;
    private Long subjectId;
    private Long facultyId;
}
