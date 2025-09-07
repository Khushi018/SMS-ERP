package com.aristiec.schoolmanagementsystem.modal.attendance;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.constant.enums.AttendanceStatus;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.timetable.Timetable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
   private LocalDate date;

    @ManyToOne
    @JoinColumn(name="student_id")
 private StudentDetails studentDetails;

    @ManyToOne
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;


  @Enumerated(EnumType.STRING)
 private AttendanceStatus attendanceStatus;
    
}
