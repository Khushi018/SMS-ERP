package com.aristiec.schoolmanagementsystem.repository.leave;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.constant.enums.DesignationEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.LeaveReasonEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.LeaveStatus;
import com.aristiec.schoolmanagementsystem.modal.leave.Leave;

@Repository
public interface LeaveRepository extends JpaRepository<Leave,Long> {
     List<Leave> findByUserIdAndDesignation(Long userId, DesignationEnum designation);
     List<Leave> findByDesignation(DesignationEnum designation);
List<Leave> findByStatus(LeaveStatus status);
List<Leave> findByLeaveReason(LeaveReasonEnum reason);
List<Leave> findByAppliDate(LocalDate appliDate);
List<Leave> findByStatusAndDesignation(LeaveStatus status, DesignationEnum designation);

}
