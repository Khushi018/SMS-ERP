package com.aristiec.schoolmanagementsystem.modal.user;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Data
public class UserDTO {
    private String name;
    private String email;
    private String mobileNo;
    private String username;
    private String password;
    private Set<String> roles;

}
