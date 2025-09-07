package com.aristiec.schoolmanagementsystem.modal.admission;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="previous_education_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreviousEducation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String lastSchoolOrCollege;
        private String qualification;
        private String boardOrUniversity;
        private Integer passingYear;
        private Double percentage;

        @ManyToOne
        @JoinColumn(name = "student_id")
        @JsonBackReference
        private StudentDetails studentDetails;

}