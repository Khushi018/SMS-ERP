package com.aristiec.schoolmanagementsystem.modal.announcement;

import java.time.LocalDateTime;

import com.aristiec.schoolmanagementsystem.constant.enums.AnnouncementType;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.staff.Department;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "announcements")
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String title;

      @Column(length = 1000)
    private String message;


     private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private AnnouncementType type;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String year;

    private String createdBy;


}
