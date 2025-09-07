package com.aristiec.schoolmanagementsystem.modal.otp;

import com.aristiec.schoolmanagementsystem.modal.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByUser(User user);
    Optional<PasswordResetToken> findByOtp(String otp);
}
