package com.aristiec.schoolmanagementsystem.controller.schedule;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.schedule.UpcomingScheduleDTO;
import com.aristiec.schoolmanagementsystem.service.schedule.ScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/upcoming")
    public ResponseEntity<UpcomingScheduleDTO> getUpcomingSchedule(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (date == null) {
            date = LocalDate.now(); // fallback if no query param
        }

        UpcomingScheduleDTO dto = scheduleService.getUpcomingSchedule(date);
        return ResponseEntity.ok(dto);
    }
}
