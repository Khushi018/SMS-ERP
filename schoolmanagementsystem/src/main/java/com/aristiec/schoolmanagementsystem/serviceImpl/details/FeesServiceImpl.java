package com.aristiec.schoolmanagementsystem.serviceImpl.details;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.constant.enums.PaymentStatus;
import com.aristiec.schoolmanagementsystem.dto.fees.FeeRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.fees.FeeSummaryDTO;
import com.aristiec.schoolmanagementsystem.dto.fees.FeesResponseDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.Fee;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.repository.details.FeesRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.service.details.FeesService;

@Service
public class FeesServiceImpl implements FeesService {
      
      @Autowired
     private FeesRepository feesRepository;
       
     @Autowired
     private StudentDetailsRepository studentDetailsRepository;

     @Override
     public FeesResponseDTO payFee(Long studentId, FeeRequestDTO feeRequestDTO) {
         StudentDetails student = studentDetailsRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));

    Fee fee = new Fee();
    fee.setAmount(feeRequestDTO.getAmount());
    fee.setPaymentStatus(feeRequestDTO.getPaymentStatus());
    fee.setPaymentDate(feeRequestDTO.getPaymentDate() != null ? feeRequestDTO.getPaymentDate() : LocalDate.now());
    fee.setStudentDetails(student);

    Fee savedFee = feesRepository.save(fee);
     
     FeesResponseDTO responseDTO = new FeesResponseDTO();
    responseDTO.setFeeId(savedFee.getFeeId());
    responseDTO.setAmount(savedFee.getAmount());
    responseDTO.setPaymentStatus(savedFee.getPaymentStatus());
    responseDTO.setPaymentDate(savedFee.getPaymentDate());
    responseDTO.setStudentId(studentId);

    return responseDTO;
    
    }

     @Override
     public List<FeesResponseDTO> getFeesByStudent(Long studentId) {
    
      List<Fee> fees = feesRepository.findByStudentDetailsId(studentId);
    List<FeesResponseDTO> feeDTOs = new ArrayList<>();

    for (Fee fee : fees) {
        FeesResponseDTO dto = new FeesResponseDTO();
        dto.setFeeId(fee.getFeeId());
        dto.setAmount(fee.getAmount());
        dto.setPaymentStatus(fee.getPaymentStatus());
        dto.setPaymentDate(fee.getPaymentDate());
        dto.setStudentId(fee.getStudentDetails().getId());
        feeDTOs.add(dto);
    }

    return feeDTOs;
    
    }

     @Override
     public FeesResponseDTO updateFee(Long feeId, FeeRequestDTO dto) {
         Fee fee = feesRepository.findById(feeId)
            .orElseThrow(() -> new RuntimeException("Fee record not found"));

    fee.setAmount(dto.getAmount());
    fee.setPaymentStatus(dto.getPaymentStatus());
    fee.setPaymentDate(dto.getPaymentDate() != null ? dto.getPaymentDate() : LocalDate.now());

    Fee saved = feesRepository.save(fee);

    FeesResponseDTO response = new FeesResponseDTO();
    response.setFeeId(saved.getFeeId());
    response.setAmount(saved.getAmount());
    response.setPaymentStatus(saved.getPaymentStatus());
    response.setPaymentDate(saved.getPaymentDate());
    response.setStudentId(saved.getStudentDetails().getId());

    return response;
     }

     @Override
     public void deleteFee(Long feeId) {
        Fee fee = feesRepository.findById(feeId)
            .orElseThrow(() -> new RuntimeException("Fee not found"));
      feesRepository.delete(fee);
    }

     @Override
     public FeeSummaryDTO getFeeSummaryByStudent(Long studentId) {
        
         StudentDetails student = studentDetailsRepository.findById(studentId)
        .orElseThrow(() -> new RuntimeException("Student not found"));

    Double totalCourseFee = student.getCourse().getTotalFees();

    List<Fee> fees = feesRepository.findByStudentDetailsId(studentId);

    Double totalPaid = fees.stream()
            .filter(fee -> fee.getPaymentStatus() == PaymentStatus.PAID)
            .mapToDouble(fee -> fee.getAmount().doubleValue())
            .sum();

    Double remaining = totalCourseFee - totalPaid;

    String studentName = student.getFirstName() + " " + student.getLastName();

    return new FeeSummaryDTO(studentId, studentName, totalCourseFee, totalPaid, remaining);

     }

     @Override
     public List<FeeSummaryDTO> getStudentsWithRemainingFees() {
        List<StudentDetails> allStudents = studentDetailsRepository.findAll();
    List<FeeSummaryDTO> studentsWithDueFees = new ArrayList<>();

    for (StudentDetails student : allStudents) {
        Double totalCourseFee = student.getCourse().getTotalFees();

        List<Fee> fees = feesRepository.findByStudentDetailsId(student.getId());
        Double totalPaid = fees.stream()
                .filter(fee -> fee.getPaymentStatus() == PaymentStatus.PAID)
                .mapToDouble(fee -> fee.getAmount().doubleValue())
                .sum();

        Double remaining = totalCourseFee - totalPaid;

        if (remaining > 0) {
            FeeSummaryDTO dto = new FeeSummaryDTO();
            dto.setStudentId(student.getId());
            dto.setStudentName(student.getFirstName() + " " + student.getLastName()); // adjust if needed
            dto.setTotalCourseFee(totalCourseFee);
            dto.setTotalPaid(totalPaid);
            dto.setRemainingFee(remaining);

            studentsWithDueFees.add(dto);

    }
    }
   return studentsWithDueFees;
     }

     @Override
     public List<FeeSummaryDTO> getFeeSummaryForAllStudents() {

     List<StudentDetails> students = studentDetailsRepository.findAll();
    List<FeeSummaryDTO> feeSummaries = new ArrayList<>();

    for (StudentDetails student : students) {
        Double totalCourseFee = student.getCourse().getTotalFees();

        List<Fee> fees = feesRepository.findByStudentDetailsId(student.getId());

        Double totalPaid = fees.stream()
            .filter(fee -> fee.getPaymentStatus() == PaymentStatus.PAID)
            .mapToDouble(fee -> fee.getAmount().doubleValue())
            .sum();

        Double remainingFee = totalCourseFee - totalPaid;

        FeeSummaryDTO summaryDTO = new FeeSummaryDTO(
            student.getId(),
            student.getFirstName() + " " + student.getLastName(),  // or any student name field
            totalCourseFee,
            totalPaid,
            remainingFee
        );

        feeSummaries.add(summaryDTO);
    }

    return feeSummaries;

    }

    
}
