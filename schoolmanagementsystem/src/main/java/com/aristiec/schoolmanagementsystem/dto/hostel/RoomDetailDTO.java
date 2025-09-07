package com.aristiec.schoolmanagementsystem.dto.hostel;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailDTO {
    private Long roomDetailId;
    private String roomNumber;
    private int studentPerRoom;
    private Double rent;
    private String roomType;     
    private String block;       
    private String floor;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Long hostelId;
    private List<RoomAmenityDTO> amenities;

}
