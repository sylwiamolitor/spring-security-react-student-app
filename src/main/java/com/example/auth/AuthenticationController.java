package com.example.auth;

import com.example.model.ForgotPasswordRequestDTO;
import com.example.model.ResetPasswordRequestDTO;
import com.example.service.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Operations on authorization and registration")
public class AuthenticationController {

    private final AuthenticationService service;
    private final PasswordResetService passwordResetService;

    @PostMapping("/register")
    @Operation(summary = "Method for user registration")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Method for user authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestBody ForgotPasswordRequestDTO request) {

        passwordResetService.initiateReset(request.getEmail());
        return ResponseEntity.ok(
                "If an account exists, you will receive a reset link."
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequestDTO request) {

        passwordResetService.resetPassword(request);
        return ResponseEntity.ok("Password successfully reset");
    }

}
