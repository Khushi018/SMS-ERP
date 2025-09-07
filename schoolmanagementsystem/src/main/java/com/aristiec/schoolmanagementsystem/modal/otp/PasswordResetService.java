package com.aristiec.schoolmanagementsystem.modal.otp;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.aristiec.schoolmanagementsystem.modal.user.User;
import com.aristiec.schoolmanagementsystem.repository.UserRepo;
import com.aristiec.schoolmanagementsystem.service.mail.EmailService;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
 private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepo userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    public String initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));

        String otp = generateOtp();

        PasswordResetToken token = tokenRepository.findByUser(user)
                .orElse(new PasswordResetToken());

        token.setUser(user);
        token.setOtp(otp);
        token.setExpiryTime(LocalDateTime.now().plusMinutes(10));
        token.setVerified(false);

        tokenRepository.save(token);

        String subject = "Aristiec School Management - Password Reset OTP";
        String message = "Dear User,\n\n"
            + "We received a request to reset your password for your Aristiec School Management account.\n"
            + "Please use the following One-Time Password (OTP) to reset your password:\n\n"
            + "OTP: " + otp + "\n\n"
            + "This OTP is valid for 10 minutes. If you did not request a password reset, please ignore this email.\n\n"
            + "Best regards,\n"
            + "Aristiec School Management Team";
        emailService.sendEmail(email, subject, message);

        return "OTP sent to email.";
    }

    public String verifyOtp(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        PasswordResetToken token = tokenRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("OTP not requested"));

        if (token.getExpiryTime().isBefore(LocalDateTime.now())) {
            return "OTP expired.";
        }

        if (!token.getOtp().equals(otp)) {
            return "Invalid OTP.";
        }

        token.setVerified(true);
        tokenRepository.save(token);
        return "OTP verified.";
    }

    @Transactional
    public String resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        PasswordResetToken token = tokenRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("OTP not verified"));

        if (!token.isVerified()) {
            return "OTP not verified.";
        }

        user.setPassword(passwordEncoder.encode(newPassword)); 
        userRepository.save(user);

        tokenRepository.delete(token);

        return "Password reset successfully.";
    }
}
