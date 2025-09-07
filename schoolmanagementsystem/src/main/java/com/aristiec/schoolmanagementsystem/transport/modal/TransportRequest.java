package com.aristiec.schoolmanagementsystem.transport.modal;

import com.aristiec.schoolmanagementsystem.constant.enums.DayOfWeek;
import com.aristiec.schoolmanagementsystem.constant.enums.RequestStatusEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.RequestTypeEnum;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transport_request")
public class TransportRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long number;

    private String email;

    private Long emergencyNo1;

    private Long emergencyNo2;

    @Enumerated(EnumType.STRING)
    private RequestTypeEnum requestType;

    @Enumerated(EnumType.STRING)
    private RequestStatusEnum requestStatus;

    private LocalDate startDate;

    private String location;

    private String pincode;

    private String landmark;

     @ManyToOne
     @JoinColumn(name = "student_id", nullable = false)
     private StudentDetails student;

    @Enumerated(EnumType.STRING)
     private DayOfWeek[] days;

}
