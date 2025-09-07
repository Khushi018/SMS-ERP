package com.aristiec.schoolmanagementsystem.modal.faculty;

import java.time.LocalDate;
import java.util.List;

import org.aspectj.weaver.Position;

import com.aristiec.schoolmanagementsystem.constant.enums.FacultyDesignation;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.staff.Department;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "faculties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String facultyId;
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private  FacultyDesignation position;
    private int experience;          
    private String qualification;   
    private double salary;

    private LocalDate joiningDate;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department; 

     @ManyToMany
    @JoinTable(
        name = "faculty_courses",
        joinColumns = @JoinColumn(name = "faculty_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
    
}
