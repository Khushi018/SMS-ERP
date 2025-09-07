package com.aristiec.schoolmanagementsystem.modal.admission;

import java.util.ArrayList;
import java.util.List;

import com.aristiec.schoolmanagementsystem.modal.assignment.Subject;
import com.aristiec.schoolmanagementsystem.modal.staff.Department;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;
    private String courseCode;
    private Integer durationInYears;
    private Double totalFees;
    private String description;
    private Boolean isSemesterSystem;
    private Boolean active = true;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<StudentDetails> studentDetails = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (active == null) {
            active = true;
        }
    }

    // course - subject - assignments
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Subject> subjects = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<StudentDetails> students;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // public String getName() {
    // return this.courseName;
    // }

}
