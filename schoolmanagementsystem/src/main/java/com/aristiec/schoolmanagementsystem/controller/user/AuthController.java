package com.aristiec.schoolmanagementsystem.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.LoginRequest;
import com.aristiec.schoolmanagementsystem.modal.user.User;
import com.aristiec.schoolmanagementsystem.modal.user.UserDTO;
import com.aristiec.schoolmanagementsystem.service.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "APIs for user and admin signup/login")

// @SecurityRequirement(name = "bearerAuth") 
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    @Operation(summary = "Create user account", description = "Registers a new regular user")
    public ResponseEntity<User> createUser(
            @RequestBody @Parameter(description = "User details") UserDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    public ResponseEntity<String> loginUser(
            @RequestBody @Parameter(description = "Login credentials") LoginRequest user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.loginUser(user));
    }
}
