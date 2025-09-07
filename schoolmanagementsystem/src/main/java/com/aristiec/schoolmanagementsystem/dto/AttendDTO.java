package com.aristiec.schoolmanagementsystem.dto;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.constant.enums.AttendanceStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Data
public class AttendDTO {
   private Long studentId;
   private Long timetableId;
   private AttendanceStatus attendanceStatus; 
   private LocalDate date;

}
