package com.aristiec.schoolmanagementsystem.modal.otp;

import lombok.RequiredArgsConstructor;


import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aristiec.schoolmanagementsystem.config.ApiResponse;

@RestController
@RequestMapping("/api/v1/password")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<ApiResponse<String>> requestOtp(@RequestParam String email) {
        String result = passwordResetService.initiatePasswordReset(email);
        ApiResponse<String> response = new ApiResponse<>("Message", result, HttpStatus.SC_OK);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<String>> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        String result = passwordResetService.verifyOtp(email, otp);
         ApiResponse<String> response = new ApiResponse<>("Message", result, HttpStatus.SC_OK);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        String result = passwordResetService.resetPassword(email, newPassword);
         ApiResponse<String> response = new ApiResponse<>("Message", result, HttpStatus.SC_OK);
        return ResponseEntity.ok(response);
    }
}
