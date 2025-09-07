package com.aristiec.schoolmanagementsystem.service.recheck;

import com.aristiec.schoolmanagementsystem.constant.enums.RecheckEnum;
import com.aristiec.schoolmanagementsystem.dto.recheck.RecheckCreateDto;
import com.aristiec.schoolmanagementsystem.dto.recheck.RecheckDto;

import java.util.List;
import java.util.Map;

public interface RecheckService {


    // Create recheck request
    RecheckDto createRecheck(RecheckCreateDto recheckCreateDto, byte[] proofBytes);

    // Get recheck by ID
    RecheckDto getRecheckById(Long id);

    // Get rechecks by student ID
    List<RecheckDto> getRechecksByStudent(Long studentId);

    // Update recheck status
    RecheckDto updateRecheckStatus(Long id, RecheckEnum status);

    // Get all pending recheck requests
    List<RecheckDto> getPendingRechecks();

    // Get rechecks by exam ID
    List<RecheckDto> getRechecksByExamId(Long examId);

    // Get rechecks by status
    List<RecheckDto> getRechecksByStatus(RecheckEnum status);

    void deleteRecheckById(Long id);

    void deleteRechecksByStudentId(Long studentId);

    void deleteRechecksByExamId(Long examId);

    byte[] getRecheckProof(Long recheckId);
}
