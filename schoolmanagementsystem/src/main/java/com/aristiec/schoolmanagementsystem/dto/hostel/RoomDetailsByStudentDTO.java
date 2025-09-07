package com.aristiec.schoolmanagementsystem.dto.hostel;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailsByStudentDTO {
    private String roomNumber;
    private String roomType;
    private String capacity;           
    private String currentOccupancy;   
    private LocalDate checkInDate;
}
