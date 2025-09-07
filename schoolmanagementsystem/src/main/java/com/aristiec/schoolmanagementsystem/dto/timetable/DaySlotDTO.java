package com.aristiec.schoolmanagementsystem.dto.timetable;

import java.util.List;

import com.aristiec.schoolmanagementsystem.constant.enums.DayOfWeek;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DaySlotDTO {
     private DayOfWeek day;
    private List<TimetableSlotDTO> slots;
}
