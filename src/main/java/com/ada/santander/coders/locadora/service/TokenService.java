package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    final String ISSUER = "ada-store";

    @Value("${jwt-secret}")
    private String secret;


    public String generateToken(User user) throws JWTCreationException {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withIssuer(ISSUER)
                    .withSubject(user.getLogin())
                    .withExpiresAt(getExpiration())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro while generating token", exception);
        }
    }

    public String validateToken(String token) throws JWTVerificationException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Error while validate token", exception);
        }
    }


    private Instant getExpiration() {
        return LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("-03:00"));
    }
}
