package com.aristiec.schoolmanagementsystem.dto.hostel;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomWithRoommatesDTO {
    private String roomNumber;
    private Double rent;
    private int studentPerRoom;
    private List<RoommateDTO> roommates;
}
