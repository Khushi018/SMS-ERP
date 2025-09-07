package com.aristiec.schoolmanagementsystem.transport.service;

import java.util.List;

import com.aristiec.schoolmanagementsystem.transport.dto.StudentTransportAssignmentDto;

public interface StudentTransportAssignmentService {
    StudentTransportAssignmentDto assign(StudentTransportAssignmentDto dto);

    List<StudentTransportAssignmentDto> getByRoute(Long routeId);

    List<StudentTransportAssignmentDto> getByStudentCode(String studentCode);

    void remove(Long id);
}
