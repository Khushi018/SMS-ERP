package com.aristiec.schoolmanagementsystem.dto.hostel;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomAllocationsDTO {
    private Long roomAllocationsId;
    private boolean isVacated;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Integer schoolId;
    private Long roomDetailId;
    private Long studentId;
}
