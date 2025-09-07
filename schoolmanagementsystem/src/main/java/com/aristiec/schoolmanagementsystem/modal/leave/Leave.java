package com.aristiec.schoolmanagementsystem.modal.leave;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.constant.enums.DesignationEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.LeaveReasonEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.LeaveStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "leave_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Leave {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private DesignationEnum designation;

      @Enumerated(EnumType.STRING)
    private LeaveReasonEnum leaveReason;

    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalDate appliDate;

     @Enumerated(EnumType.STRING)
    private LeaveStatus status;
    
}
