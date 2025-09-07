package com.aristiec.schoolmanagementsystem.service.details;

import java.util.List;

import com.aristiec.schoolmanagementsystem.dto.fees.FeeRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.fees.FeeSummaryDTO;
import com.aristiec.schoolmanagementsystem.dto.fees.FeesResponseDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.Fee;

public interface FeesService {
 FeesResponseDTO payFee(Long studentId, FeeRequestDTO feeRequestDTO);
 List<FeesResponseDTO> getFeesByStudent(Long studentId);
 FeesResponseDTO updateFee(Long feeId, FeeRequestDTO dto);
 void deleteFee(Long feeId);
 FeeSummaryDTO getFeeSummaryByStudent(Long studentId);
 List<FeeSummaryDTO> getStudentsWithRemainingFees();
 List<FeeSummaryDTO> getFeeSummaryForAllStudents();


} 