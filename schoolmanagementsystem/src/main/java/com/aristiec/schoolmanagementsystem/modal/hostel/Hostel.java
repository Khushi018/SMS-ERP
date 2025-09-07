package com.aristiec.schoolmanagementsystem.modal.hostel;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "hostels")
@RequiredArgsConstructor
public class Hostel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long hostelId;
    private String hostelName;
    private String hostelType;
    private String otherInfo;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "hostel", cascade = CascadeType.ALL)
    private List<RoomDetail> roomDetail;

}
