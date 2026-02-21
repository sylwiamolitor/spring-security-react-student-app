package com.example.service;

import com.example.entity.PasswordResetToken;
import com.example.entity.User;
import com.example.model.ResetPasswordRequestDTO;
import com.example.repository.PasswordResetTokenRepository;
import com.example.repository.UserRepository;
import com.example.util.TokenGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final TokenGenerator tokenGenerator;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public void initiateReset(String email) {

        userRepository.findByEmail(email).ifPresent(user -> {

            String rawToken = tokenGenerator.generateSecureToken();
            String hashedToken = passwordEncoder.encode(rawToken);

            PasswordResetToken token = new PasswordResetToken();
            token.setUserId(user.getID());
            token.setTokenHash(hashedToken);
            token.setExpiryDate(LocalDateTime.now().plusMinutes(20));
            token.setUsed(false);

            tokenRepository.save(token);
            emailService.sendResetLink(email, rawToken);
        });
    }

    @Transactional
    public void resetPassword(ResetPasswordRequestDTO resetPasswordRequestDTO) {

        User user = userRepository.findByEmail(resetPasswordRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        PasswordResetToken token = getValidToken(user.getID(), resetPasswordRequestDTO.getToken());

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid or expired token");
        }

        user.setPassword(passwordEncoder.encode(resetPasswordRequestDTO.getNewPassword()).toCharArray());
        userRepository.save(user);

        token.setUsed(true);
        tokenRepository.save(token);
    }

    public PasswordResetToken getValidToken(Long userId, String rawToken) {

        List<PasswordResetToken> tokens =
                new ArrayList<>(tokenRepository.findByUserIdAndUsedFalse(userId));

        if (tokens.isEmpty()) {
            throw new RuntimeException("No unused or unexpired password reset token found for this user");
        }

        return tokens.stream()
                .filter(token -> passwordEncoder.matches(rawToken, token.getTokenHash()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid password reset token"));
    }

}
