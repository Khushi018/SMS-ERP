package com.aristiec.schoolmanagementsystem.transport.modal;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "student_transport_assignments")
@Data
public class StudentTransportAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StudentDetails student;
    @ManyToOne
    private Route route;
    @ManyToOne
    private Stoppage stoppage;
    @ManyToOne
    private Vehicle vehicle;
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentStoppageDropOff> stoppageDropOffs = new ArrayList<>();

}