package com.aristiec.schoolmanagementsystem.modal.hostel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Entity
@Data
@Table(name = "room_details")
@RequiredArgsConstructor
public class RoomDetail {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long roomDetailId;
    private String roomNumber;
    private int studentPerRoom;
    private Double rent;
     private String block;       // New field for block
    private String floor;
      private String roomType;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "roomDetail", cascade = CascadeType.ALL)
    private List<RoomAllocations> roomAllocations;

    @ManyToOne
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;

    @OneToMany(mappedBy = "roomDetail", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<RoomAmenity> amenities;



}
