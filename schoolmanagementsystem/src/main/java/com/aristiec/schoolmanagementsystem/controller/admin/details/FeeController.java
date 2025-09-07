package com.aristiec.schoolmanagementsystem.controller.admin.details;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.fees.FeeRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.fees.FeeSummaryDTO;
import com.aristiec.schoolmanagementsystem.dto.fees.FeesResponseDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.Fee;
import com.aristiec.schoolmanagementsystem.service.details.FeesService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/fees")

@SecurityRequirement(name = "bearerAuth") 
public class FeeController {
     
     @Autowired
    private FeesService feesService;

     @PostMapping("/pay/{studentId}")
    public ResponseEntity<FeesResponseDTO> payFee(@PathVariable Long studentId,@RequestBody FeeRequestDTO feeRequestDTO){
        return ResponseEntity.ok(feesService.payFee(studentId, feeRequestDTO));
    }

     @GetMapping("/student/{studentId}")
    public ResponseEntity<List<FeesResponseDTO>> getFeesByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(feesService.getFeesByStudent(studentId));
    }

     @PutMapping("/update/{feeId}")
public ResponseEntity<FeesResponseDTO> updateFee(
        @PathVariable Long feeId,
        @RequestBody FeeRequestDTO feeRequestDTO) {
    FeesResponseDTO updated = feesService.updateFee(feeId, feeRequestDTO);
    return ResponseEntity.ok(updated);
}

  @DeleteMapping("/delete/{feeId}")
  public ResponseEntity<String> deleteFee(@PathVariable Long feeId) {
    feesService.deleteFee(feeId);
    return ResponseEntity.ok("Fee record deleted");
    }

     @GetMapping("/summary/{studentId}")
public ResponseEntity<FeeSummaryDTO> getFeeSummary(@PathVariable Long studentId) {
    return ResponseEntity.ok(feesService.getFeeSummaryByStudent(studentId));
}

 @GetMapping("/admin/remaining-fees")
    public List<FeeSummaryDTO> getStudentsWithRemainingFees() {
        return feesService.getStudentsWithRemainingFees();
    }

    @GetMapping("/admin/fee-summary")
public ResponseEntity<List<FeeSummaryDTO>> getFeeSummaryForAllStudents() {
    List<FeeSummaryDTO> summaries = feesService.getFeeSummaryForAllStudents();
    return ResponseEntity.ok(summaries);
}



}
