package com.aristiec.schoolmanagementsystem.modal.admission;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="parents_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"studentDetails"})
public class ParentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fatherName;
    private String fatherOccupation;
    private String fatherContact;
    private String email;

    private String motherName;
    private String motherOccupation;
    private String motherContact;

    private String guardianName;
    private String guardianContact;
    private String guardianRelation;

    @OneToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private StudentDetails studentDetails;


}
