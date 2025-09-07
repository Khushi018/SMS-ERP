package com.aristiec.schoolmanagementsystem.modal.hostel;


import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
@Entity
@Data
@Table(name = "room_allocations")
@RequiredArgsConstructor
public class RoomAllocations {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomAllocationsId;
    private boolean isVacated;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Integer schoolId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_detail_id")
    private RoomDetail roomDetail;

    @OneToOne
    @JoinColumn(name = "student_id")
    private StudentDetails studentDetails;
}
