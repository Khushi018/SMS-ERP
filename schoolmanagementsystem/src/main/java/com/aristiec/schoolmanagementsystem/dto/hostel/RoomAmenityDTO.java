package com.aristiec.schoolmanagementsystem.dto.hostel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomAmenityDTO {
    private String amenityName;
    private String description;
    private boolean available;
}
