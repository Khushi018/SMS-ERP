package com.aristiec.schoolmanagementsystem.dto.timetable;

import java.util.List;

import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimetableResponseDTO {
    private Integer semester;
    private String courseName;
    private SectionEnum section;
    private List<DaySlotResponseDTO> slots;
}
