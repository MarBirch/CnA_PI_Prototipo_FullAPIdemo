package com.example.FullAPIdemo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Centraliza hash de senhas. Aceita senhas legadas em texto puro apenas para
 * migracao transparente: no primeiro login valido a senha e convertida para BCrypt.
 */
@Service
public class PasswordService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String hash(String raw) { return encoder.encode(raw); }

    public boolean isHash(String stored) { return stored != null && stored.matches("^\\$2[aby]\\$.{56}$"); }

    public boolean matches(String raw, String stored) {
        if (raw == null || stored == null) return false;
        if (isHash(stored)) return encoder.matches(raw, stored);
        return MessageDigest.isEqual(raw.getBytes(StandardCharsets.UTF_8), stored.getBytes(StandardCharsets.UTF_8));
    }

    public boolean precisaMigrar(String stored) { return !isHash(stored); }
}
