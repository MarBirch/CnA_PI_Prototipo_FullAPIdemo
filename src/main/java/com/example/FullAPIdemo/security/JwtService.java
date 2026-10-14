package com.example.FullAPIdemo.security;

import com.example.FullAPIdemo.model.Papel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {
    private final SecretKey key;
    private final long expirationMinutes;

    public JwtService(@Value("${app.jwt.secret}") String secret,
                      @Value("${app.jwt.expiration-minutes:480}") long expirationMinutes) {
        if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalStateException("JWT_SECRET deve ter pelo menos 32 caracteres.");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
    }

    public long getExpirationSeconds() { return expirationMinutes * 60; }

    public String gerar(AuthPrincipal p) {
        Instant agora = Instant.now();
        return Jwts.builder().subject(String.valueOf(p.id()))
                .claim("email", p.email()).claim("papel", p.papel().name())
                .issuedAt(Date.from(agora)).expiration(Date.from(agora.plusSeconds(getExpirationSeconds())))
                .signWith(key).compact();
    }

    public Optional<AuthPrincipal> validar(String token) {
        try {
            Claims c = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            return Optional.of(new AuthPrincipal(Long.valueOf(c.getSubject()), c.get("email", String.class),
                    Papel.valueOf(c.get("papel", String.class))));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
