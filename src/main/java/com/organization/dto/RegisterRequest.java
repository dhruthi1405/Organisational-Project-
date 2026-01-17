package com.organization.dto;

import com.organization.enums.Role;  // Fixed import statement
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String username;
    private String password;
    private Role role; 
}
