package com.shehia_management.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String zanId;
    private String role;
    private String tokenType = "Bearer";

    // Constructor ya parameter 3 inayotumiwa na AuthController
    public LoginResponse(String token, String zanId, String role) {
        this.token = token;
        this.zanId = zanId;
        this.role = role;
        this.tokenType = "Bearer";
    }

    // Optional: Constructor ya parameter 4 kama utahitaji kubadilisha tokenType siku zijazo
    public LoginResponse(String token, String zanId, String role, String tokenType) {
        this.token = token;
        this.zanId = zanId;
        this.role = role;
        this.tokenType = tokenType;
    }
}