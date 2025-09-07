package com.aristiec.schoolmanagementsystem.transport.service;

import java.util.List;

import com.aristiec.schoolmanagementsystem.transport.dto.StudentStoppageDropOffDto;

public interface StudentStoppageDropOffService {
    StudentStoppageDropOffDto assignDropOff(Long assignmentId, StudentStoppageDropOffDto dto);

    List<StudentStoppageDropOffDto> getDropOffsByAssignmentId(Long assignmentId);
}
