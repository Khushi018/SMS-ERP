package com.aristiec.schoolmanagementsystem.dto.leave;

import com.aristiec.schoolmanagementsystem.constant.enums.DesignationEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.LeaveReasonEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.LeaveStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveResponseDTO {
     private Long id;
    private String userName;
    private DesignationEnum designation;
    private LeaveReasonEnum leaveReason;
    private String fromDate;
    private String toDate;
    private String appliDate;
    private LeaveStatus status;
}
