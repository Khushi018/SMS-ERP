package com.aristiec.schoolmanagementsystem.repository.staff;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.constant.enums.StaffDesignation;
import com.aristiec.schoolmanagementsystem.dto.adminReport.DepartmentStaffFacultyCountDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.DesignationCountDTO;
import com.aristiec.schoolmanagementsystem.modal.staff.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {
  
    List<Staff>findByDepartmentId(Long dapartmentId);
    List<Staff> findByDesignation(StaffDesignation designation);
    
    long countByDepartmentId(Long departmentId);
   long countByDesignation(StaffDesignation designation);


    long countByDesignationIn(List<StaffDesignation> designations);

    
} 
