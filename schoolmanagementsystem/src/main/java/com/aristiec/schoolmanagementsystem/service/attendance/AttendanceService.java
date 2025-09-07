package com.aristiec.schoolmanagementsystem.service.attendance;

import java.util.List;


import com.aristiec.schoolmanagementsystem.dto.AttendDTO;
import com.aristiec.schoolmanagementsystem.dto.OverallAttendanceDTO;
import com.aristiec.schoolmanagementsystem.dto.attendance.AttendanceResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.attendance.SubjectwiseAttendanceDTO;
import com.aristiec.schoolmanagementsystem.modal.attendance.Attendance;


public interface AttendanceService {
 
     void markAttendance(List<AttendDTO> dto);

    AttendanceResponseDTO getAttendanceById(Long id);

    List<AttendanceResponseDTO>getAllAttendances();

    boolean deleteAttendance(Long id);

    String updateAttendance(Long id, AttendDTO dto);

    OverallAttendanceDTO getOverallAttendance(Long studentId);

    List<SubjectwiseAttendanceDTO> getSubjectWiseAttendance(Long studentId);

    OverallAttendanceDTO getMonthlyOverallAttendance(Long studentId, int month, int year);
    
}
