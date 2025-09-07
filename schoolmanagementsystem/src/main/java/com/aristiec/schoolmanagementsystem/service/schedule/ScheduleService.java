package com.aristiec.schoolmanagementsystem.service.schedule;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.dto.schedule.UpcomingScheduleDTO;

public interface ScheduleService {
    UpcomingScheduleDTO getUpcomingSchedule(LocalDate date);
}
