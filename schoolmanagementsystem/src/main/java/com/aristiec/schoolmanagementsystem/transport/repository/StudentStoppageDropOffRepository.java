package com.aristiec.schoolmanagementsystem.transport.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.transport.modal.StudentStoppageDropOff;

public interface StudentStoppageDropOffRepository extends JpaRepository<StudentStoppageDropOff, Long> {
    List<StudentStoppageDropOff> findByAssignment_Id(Long assignmentId);

    Optional<StudentStoppageDropOff> findByAssignmentIdAndDropOffTrue(Long assignmentId);

    void deleteByAssignment_Id(Long assignmentId);

    void deleteByAssignment_IdAndStoppage_Id(Long assignmentId, Long stoppageId);

    boolean existsByAssignment_IdAndStoppage_Id(Long assignmentId, Long stoppageId);

}
