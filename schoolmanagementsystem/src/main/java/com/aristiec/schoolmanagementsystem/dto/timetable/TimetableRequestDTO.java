package com.aristiec.schoolmanagementsystem.dto.timetable;

import java.util.List;

import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimetableRequestDTO {
     private Long courseId;
    private SectionEnum section;
     private Integer semester;
    private List<DaySlotDTO> slots;
}
