package com.aristiec.schoolmanagementsystem.repository.hostel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.constant.enums.HostelStaffDesignation;
import com.aristiec.schoolmanagementsystem.modal.hostel.HostelStaff;

@Repository
public interface HostelStaffRepository extends JpaRepository<HostelStaff, Long> {
    List<HostelStaff> findByHostel_HostelId(Long hostelId);
    List<HostelStaff> findByHostel_HostelIdAndDesignationIn(Long hostelId, List<HostelStaffDesignation> designations);

}
