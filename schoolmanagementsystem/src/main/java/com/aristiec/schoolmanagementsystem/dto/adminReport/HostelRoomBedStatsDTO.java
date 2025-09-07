package com.aristiec.schoolmanagementsystem.dto.adminReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostelRoomBedStatsDTO {
    private int totalHostels;
    private int totalRooms;
    private int totalBeds;
}
