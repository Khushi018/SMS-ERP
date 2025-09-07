package com.aristiec.schoolmanagementsystem.controller.recheck;

import com.aristiec.schoolmanagementsystem.constant.enums.RecheckEnum;
import com.aristiec.schoolmanagementsystem.dto.recheck.RecheckCreateDto;
import com.aristiec.schoolmanagementsystem.dto.recheck.RecheckDto;
import com.aristiec.schoolmanagementsystem.service.recheck.RecheckService;
import com.aristiec.schoolmanagementsystem.serviceImpl.recheck.RecheckServiceImpl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/recheck")
@SecurityRequirement(name = "bearerAuth")   

@Tag(name = "Recheck Management", description = "Manage exam recheck requests and their related details")     

public class RecheckController {
    @Autowired
    private RecheckServiceImpl recheckService;

    // Create recheck request
    @PostMapping("/create")
    public ResponseEntity<RecheckDto> createRecheck(
            @ModelAttribute RecheckCreateDto recheckCreateDto,
            @RequestParam("proof") MultipartFile proof) {

        if (proof.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        byte[] proofBytes;
        try {
            proofBytes = proof.getBytes();
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        RecheckDto recheckDto = recheckService.createRecheck(recheckCreateDto, proofBytes);
        return new ResponseEntity<>(recheckDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/proof")
    public ResponseEntity<byte[]> getRecheckProof(@PathVariable("id") Long recheckId) {
        try {
            byte[] proofBytes = recheckService.getRecheckProof(recheckId);

            // If proof is not available
            if (proofBytes == null || proofBytes.length == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            // Set response headers for content type and content length
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(proofBytes.length);

            // Return image as byte array in response body
            return new ResponseEntity<>(proofBytes, headers, HttpStatus.OK);

        } catch (EntityNotFoundException ex) {
            // Handle case where Recheck entity is not found
            return new ResponseEntity<>(ex.getMessage().getBytes(), HttpStatus.NOT_FOUND);
        }
    }

    // Get recheck details by ID
    @GetMapping("/{id}")
    public ResponseEntity<RecheckDto> getRecheckDetails(@PathVariable Long id) {
        try {
            RecheckDto recheckDto = recheckService.getRecheckById(id);
            return new ResponseEntity<>(recheckDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all rechecks for a specific student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<RecheckDto>> getRechecksByStudent(@PathVariable Long studentId) {
        List<RecheckDto> recheckDtos = recheckService.getRechecksByStudent(studentId);
        return new ResponseEntity<>(recheckDtos, HttpStatus.OK);
    }

    // Update recheck status (ACCEPTED/REJECTED)
    @PutMapping("/{id}/status")
    public ResponseEntity<RecheckDto> updateRecheckStatus(@PathVariable Long id, @RequestParam RecheckEnum status) {
        try {
            RecheckDto updatedRecheck = recheckService.updateRecheckStatus(id, status);
            return new ResponseEntity<>(updatedRecheck, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all pending recheck requests
    @GetMapping("/pending")
    public ResponseEntity<List<RecheckDto>> getPendingRechecks() {
        List<RecheckDto> pendingRechecks = recheckService.getPendingRechecks();
        return new ResponseEntity<>(pendingRechecks, HttpStatus.OK);
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<RecheckDto>> getRechecksByExamId(@PathVariable Long examId) {
        List<RecheckDto> recheckDtos = recheckService.getRechecksByExamId(examId);

        return new ResponseEntity<>(recheckDtos, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RecheckDto>> getRechecksByStatus(@PathVariable RecheckEnum status) {
        List<RecheckDto> recheckDtos = recheckService.getRechecksByStatus(status);
        return new ResponseEntity<>(recheckDtos, HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}/summary")
    public ResponseEntity<Map<String, Long>> getStudentRecheckSummary(@PathVariable Long studentId) {
        Map<String, Long> summary = recheckService.getStudentRecheckSummary(studentId);
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecheckById(@PathVariable Long id) {
        try {
            recheckService.deleteRecheckById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/student/{studentId}")
    public ResponseEntity<Void> deleteRechecksByStudentId(@PathVariable Long studentId) {
        recheckService.deleteRechecksByStudentId(studentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/exam/{examId}")
    public ResponseEntity<Void> deleteRechecksByExamId(@PathVariable Long examId) {
        recheckService.deleteRechecksByExamId(examId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
