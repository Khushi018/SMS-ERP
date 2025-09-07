package com.aristiec.schoolmanagementsystem.transport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.transport.modal.StudentTransportAssignment;

public interface StudentTransportAssignmentRepository extends JpaRepository<StudentTransportAssignment, Long> {
    List<StudentTransportAssignment> findByRouteId(Long routeId);

    List<StudentTransportAssignment> findByStudent_StudentCode(String studentCode);
}
