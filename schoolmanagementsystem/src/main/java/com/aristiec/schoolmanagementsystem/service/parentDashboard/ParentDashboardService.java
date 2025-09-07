package com.aristiec.schoolmanagementsystem.service.parentDashboard;

import java.util.List;
import java.util.Map;

import com.aristiec.schoolmanagementsystem.dto.AssignmentDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.ParentDashboardDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.SubjectAttendanceDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.TotalAttendanceDTO;

public interface ParentDashboardService {

    ParentDashboardDTO getParentDashboardDTO(Long parentId,Long studentId);
    List<AssignmentDTO>getAssignmentsForStudent(Long parentId,Long studentId);
    TotalAttendanceDTO getTotalAttendanceByStudent(Long studentId);
    List<SubjectAttendanceDTO> getSubjectAttendanceByStudent(Long studentId);
    
} 