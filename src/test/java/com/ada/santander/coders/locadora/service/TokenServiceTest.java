package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private User user;

    private final String secret = "test-secret";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenService = new TokenService();
        tokenService.secret = secret;
    }

    @Test
    void testGenerateToken() {
        when(user.getLogin()).thenReturn("test_user");

        String token = tokenService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testValidateTokenWithValidToken() {
        when(user.getLogin()).thenReturn("test_user");

        String token = tokenService.generateToken(user);
        String subject = tokenService.validateToken(token);

        assertEquals("test_user", subject);
    }

    @Test
    void testValidateTokenWithInvalidToken() {
        String invalidToken = "invalid.token.value";

        assertThrows(RuntimeException.class, () -> tokenService.validateToken(invalidToken));
    }

    @Test
    void testValidateTokenWithExpiredToken() {
        TokenService tokenServiceExpired = new TokenService();
        tokenServiceExpired.secret = secret;

        Instant expiredInstant = LocalDateTime.now().minusSeconds(5).toInstant(ZoneOffset.of("-03:00"));

        when(user.getLogin()).thenReturn("test_user");

        String expiredToken = JWT.create()
                .withIssuer("ada-store")
                .withSubject(user.getLogin())
                .withExpiresAt(expiredInstant)
                .sign(Algorithm.HMAC256(secret));

        assertThrows(RuntimeException.class, () -> tokenServiceExpired.validateToken(expiredToken));
    }
}
