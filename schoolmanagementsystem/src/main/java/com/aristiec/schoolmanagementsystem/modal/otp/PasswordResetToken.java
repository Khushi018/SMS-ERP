package com.aristiec.schoolmanagementsystem.modal.otp;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.aristiec.schoolmanagementsystem.modal.user.User;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String otp;

    private LocalDateTime expiryTime;

    private boolean verified = false;

    @OneToOne
    private User user;


}

