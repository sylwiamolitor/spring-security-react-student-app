package com.example.repository;

import com.example.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PasswordResetTokenRepository
        extends JpaRepository<PasswordResetToken, Long> {
    Collection<PasswordResetToken> findByUserIdAndUsedFalse(Long userId);
}
