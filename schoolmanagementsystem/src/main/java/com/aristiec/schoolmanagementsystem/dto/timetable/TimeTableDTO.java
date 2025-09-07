package com.aristiec.schoolmanagementsystem.dto.timetable;

import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeTableDTO {
     private String day;
    private String startTime;
    private String endTime;
    private String subjectName;
    private String facultyName;
    private SectionEnum section;
}
