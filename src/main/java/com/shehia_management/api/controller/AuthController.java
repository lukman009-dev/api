package com.shehia_management.api.controller;

import com.shehia_management.api.config.JwtConfig;
import com.shehia_management.api.dto.LoginRequest;
import com.shehia_management.api.dto.LoginResponse;
import com.shehia_management.api.entity.User;
import com.shehia_management.api.enums.UserStatus;
import com.shehia_management.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtConfig jwtConfig;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByZanId(loginRequest.getZanId())
                .orElse(null);

        // Validate credentials
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid credentials", "Authentication failed"));
        }

        // Check if user account is suspended
        if (user.getStatus() == UserStatus.SUSPENDED) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Account suspended", "Your account has been suspended by administrator"));
        }

        // Generate JWT token
        String token = jwtConfig.generateToken(user.getZanId(), user.getRole().toString());
        LoginResponse response = new LoginResponse(token, user.getZanId(), user.getRole().toString());
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(new SuccessResponse("Logged out successfully"));
    }

    // ============================================================================
    // Helper Response Classes
    // ============================================================================
    
    public static class SuccessResponse {
        public String message;
        
        public SuccessResponse(String message) {
            this.message = message;
        }
    }

    public static class ErrorResponse {
        public String error;
        public String message;
        
        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }
    }
}
