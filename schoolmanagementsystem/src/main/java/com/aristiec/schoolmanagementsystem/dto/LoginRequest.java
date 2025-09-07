package com.aristiec.schoolmanagementsystem.dto;

import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Data
public class LoginRequest {
    private String userName;
    private String password;
}
