package com.aristiec.schoolmanagementsystem.modal.studentSupport;

import java.time.LocalDateTime;

import com.aristiec.schoolmanagementsystem.constant.enums.QueryCategory;
import com.aristiec.schoolmanagementsystem.constant.enums.QuerySendTo;
import com.aristiec.schoolmanagementsystem.constant.enums.QueryStatus;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @Enumerated(EnumType.STRING)
    private QuerySendTo sendTo;
    
     @Enumerated(EnumType.STRING)
    private QueryCategory category;

    private String subject;
     
    @Lob
    private String description;

  @Enumerated(EnumType.STRING)
private QueryStatus status=QueryStatus.OPEN;

    private LocalDateTime submittedAt;
    private LocalDateTime repliedAt;

    
    @Lob
    private byte[] fileData;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StudentDetails student;

}
