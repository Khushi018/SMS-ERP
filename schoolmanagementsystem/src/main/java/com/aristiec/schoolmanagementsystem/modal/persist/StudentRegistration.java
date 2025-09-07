package com.aristiec.schoolmanagementsystem.modal.persist;

import com.aristiec.schoolmanagementsystem.constant.enums.StudentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "student_registration")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String fullName;
    private String fatherName;
    private String contactNumber;
    private String email;
    private String address;
    private String gender;
    private String course;
    private String category;

    @Enumerated(EnumType.STRING)
    private StudentStatus status;
    private LocalDate visitingDate;

     


}
