package com.aristiec.schoolmanagementsystem.service.staff;

import java.util.List;

import com.aristiec.schoolmanagementsystem.constant.enums.StaffDesignation;
import com.aristiec.schoolmanagementsystem.dto.staff.StaffDTO;
import com.aristiec.schoolmanagementsystem.modal.staff.Staff;

public interface StaffService {

    Staff addStaff(StaffDTO staffDTO);
    List<Staff>getAllStaff();
    List<Staff>getstaffByDepartment(Long departmentId);
    Staff getStaffById(Long id);
    void deleteStaff(Long id);
    Staff updateStaff(Long id,StaffDTO staffDTO);
    List<Staff> findByDesignation(StaffDesignation designation);


    
} 