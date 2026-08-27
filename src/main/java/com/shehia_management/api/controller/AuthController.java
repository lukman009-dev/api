package com.shehia_management.api.controller;

import com.shehia_management.api.dto.LoginRequest;
import com.shehia_management.api.dto.LoginResponse;
import com.shehia_management.api.entity.User;
import com.shehia_management.api.enums.UserStatus;
import com.shehia_management.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByZanId(loginRequest.getZanId())
                .orElse(null);

        // Validate credentials (direct check for local practice)
        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid credentials", "Authentication failed"));
        }

        // Check if user account is suspended
        if (user.getStatus() == UserStatus.SUSPENDED) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Account suspended", "Your account has been suspended by administrator"));
        }

        // Pass a mock token string so your frontend or Postman still receives the LoginResponse properly
        String dummyToken = "mock-session-token-" + user.getZanId();
        LoginResponse response = new LoginResponse(dummyToken, user.getZanId(), user.getRole().toString());

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